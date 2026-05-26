package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
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
	
	
	@GetMapping("/list/empName")
	public String showListLike(String empName, Model model) {
		model.addAttribute("employees", employeeRepository.findByEmpNameContaining(empName));
		return "list/list";
	}
	
	@RequestMapping("/regist/input")
	public String createInput(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
		employeeForm.setGender(1);
		employeeForm.setAuthority(1);
		return "regist/regist_input";
	}
	

	
	@RequestMapping(path = "/regist/complete", method = RequestMethod.POST)
	public String createComplete(EmployeeForm form, Model model) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee, "empId");
		
		// ⭕ if文を使わず、直接Departmentオブジェクトを生成してセット
		Department department = new Department();
		department.setDeptId(form.getDeptId());
		employee.setDepartment(department);
		
		// データベースに保存
		employee = employeeRepository.save(employee);
		
		EmployeeBean employeeBean = new EmployeeBean();
		BeanUtils.copyProperties(employee, employeeBean);
		
		
		// 完了画面表示用にBeanへ値を移送
		employeeBean.setDeptId(employee.getDepartment().getDeptId());
		
		model.addAttribute("employee", employeeBean);
		
		return "regist/regist_complete";
	}

	@RequestMapping("regist/check")
	public String createCheckHidden(EmployeeForm form,Model model) {
		model.addAttribute("employee", form);
		return "regist/regist_check";
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
