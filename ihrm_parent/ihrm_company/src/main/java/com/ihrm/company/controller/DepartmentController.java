package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xf
 * @create 2020-03-01-14:48
 */
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RequestMapping(value = "/company")
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    //保存
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department){
        //设置企业id

         department.setCompanyId(companyId);
        //调用service完成保存
        departmentService.save(department);
        return  new Result(ResultCode.SUCCESS);
    }
    //查询企业部门列表
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
        //指定企业id
        //完成查询
        List<Department> depts = departmentService.findAll(companyId);
        System.out.println(depts);
        Company company = companyService.findById(companyId);
        //构造返回结果
        DeptListResult deptListResult = new DeptListResult(company,depts); //企业对象 以及企业下部门
        return  new Result(ResultCode.SUCCESS,deptListResult);
    }
    //根据id查询
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    //修改部门
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Department department){
        //设置修改部门id
        department.setId(id);
        //调用service完成更新
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id删除
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
