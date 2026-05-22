package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.EmpWithDep;



public interface EmpWithDepRepository extends JpaRepository<EmpWithDepRepository, Integer>{
	List<EmpWithDep> findByEmployeeDepartment(Department department);
}
