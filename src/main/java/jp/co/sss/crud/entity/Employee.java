package jp.co.sss.crud.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	private Integer empId;

	@Column
	private String empPass;

	@Column
	private String empName;

	@Column
	private Integer gender;

	@Column
	private String address;

	@Column
	private Date birthday;

	@Column
	private Integer authority;
	
	 public Integer getEmpId() {
	        return empId;
	    }

	    public void setEmpId(Integer empId) {
	        this.empId = empId;
	    }

	    public String getEmpPass() {
	        return empPass;
	    }

	    public void setEmpPass(String empPass) {
	        this.empPass = empPass;
	    }

	    public String getEmpName() {
	        return empName;
	    }

	    public void setEmpName(String empName) {
	        this.empName = empName;
	    }

	    public Integer getGender() {
	        return gender;
	    }

	    public void setGender(Integer gender) {
	        this.gender = gender;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public Date getBirthday() {
	        return birthday;
	    }

	    public void setBirthday(Date birthday) {
	        this.birthday = birthday;
	    }

	    public Integer getAuthority() {
	        return authority;
	    }

	    public void setAuthority(Integer authority) {
	        this.authority = authority;
	    }

}
