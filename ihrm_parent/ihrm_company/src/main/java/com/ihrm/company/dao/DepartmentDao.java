package com.ihrm.company.dao;

import com.ihrm.domain.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xf
 * @create 2020-03-01-14:33
 */
public interface DepartmentDao extends JpaRepository<Department,String> ,JpaSpecificationExecutor<Department> {
}
