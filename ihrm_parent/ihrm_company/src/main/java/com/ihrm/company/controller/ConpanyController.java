package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xf
 * @create 2020-02-28-14:15
 */
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/company")
public class ConpanyController {

    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody Company company){  //请求参数自动封装 要求:和实体类参数一致
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id更新
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Company company){
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }
    //删除
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
    //根据id查询企业
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Company company = companyService.findById(id);
        Result  result = new Result(ResultCode.SUCCESS);
        result.setData( company);
        return result;
    }

    //查询全部企业
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll(){
        List<Company> companyList = companyService.findAll();
        Result  result = new Result(ResultCode.SUCCESS,companyList);
        return  result;
    }
}
