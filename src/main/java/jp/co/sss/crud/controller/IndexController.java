package jp.co.sss.crud.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {

	@Autowired
	EmployeeRepository employeeRepository;
	
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showList(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "list/list"; 
    }
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = "/list/search", method = {RequestMethod.GET, RequestMethod.POST})
	public String showListSearch(String empName, Model model) {
		List<Employee> empList;
		if (empName != null && !empName.isEmpty()) {
			empList = employeeRepository.findByEmpName("%" + empName + "%");
			model.addAttribute("searchedName", empName);
		} else {
			empList = employeeRepository.findAll();
		}
		
		model.addAttribute("empList", empList);
		return "list/list"; 
	}


	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}
	

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {
		int empId = loginForm.getEmpId();
		String empPass = loginForm.getEmpPass();
		Employee employee = employeeRepository.findByEmpIdAndEmpPass(empId, empPass);

		if (employee != null) {
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setEmpId(employee.getEmpId());
			employeeBean.setEmpName(employee.getEmpName());
			employeeBean.setAuthority(employee.getAuthority());
			session.setAttribute("user", employeeBean);
			// 一覧へリダイレクト
			return "redirect:/list";

		} else {
			model.addAttribute("errMessage", "社員ID、またはパスワードが間違っています。");
			return "index";
		}

	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout() {
		// セッションの破棄
		session.invalidate();
		return "redirect:/";
	}
}
