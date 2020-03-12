package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.util.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xf
 * @create 2020-03-01-14:37
 */
@Service
public class DepartmentService extends BaseService{
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 保存部门
     */
    public void save(Department department){
        //设置主键值
        String id = idWorker.nextId() + "";
        //调用dao保存
        department.setId(id);
        departmentDao.save(department);

    }

    /**
     * 更新部门
     */
    public void update(Department department){
        //id查询部门
        Department dept = departmentDao.findById(department.getId()).get();
        //设置属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());
        //更新部门
        departmentDao.save(dept);

    }
    /**
     * id查询部门
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }
    /**
     * 查询全部部门列表
     */
    public List<Department> findAll(String companyId){
        /**
         * 条件查询
         *         只查询companyId
         *         很多地方都需要companyId查询
         *         很多对象中有companyId
         */
//        Specification<Department> spec = new Specification<Department>() {
//            @Override
//            /**
//             * 用于构造查询条件
//             *      root :包含所有对象属性
//             *      cq   :一般不用
//             *      cb   :构造查询条件
//             *          company_id = "1"
//             */
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                //根据企业id查询
//                return cb.equal(root.get("companyId").as(String.class),companyId);
//            }
//        };
        Specification spec = getSpec(companyId);
        return departmentDao.findAll(spec);
    }
    /**
     * 根据id删除部门
     */
    public void deleteById(String id){
         departmentDao.deleteById(id);
    }
}
