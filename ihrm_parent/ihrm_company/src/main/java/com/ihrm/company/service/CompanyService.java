package com.ihrm.company.service;

import com.ihrm.common.util.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xf
 * @create 2020-02-28-13:37
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 保存企业
     */
    public void add(Company company){
        //基本设置
        //1.配置idWorker到当前工程
        String id = String.valueOf(idWorker.nextId());
        //2.在service中注入idWork
        //3.生成id
        company.setId(id);
        //默认状态
        company.setAuditState("0"); //已审核
        company.setState(1); //已激活
        //4.保存企业
        companyDao.save(company);

    }
    /**
     * 更新企业
     * 根据id查询对象
     * 设置修改属性
     */
    public void update(Company company){
        Company temp = companyDao.findById(company.getId()).get();
        temp.setName(company.getName());
        temp.setCompanyPhone(company.getCompanyPhone());
        companyDao.save(temp);
    }
    /**
     * 删除企业
     */
    public void deleteById(String id){
        companyDao.deleteById(id);
    }
    /**
     * 根据id查询企业
     */
    public Company findById(String id){
        return companyDao.findById(id).get();
    }
    /**
     * 查询企业列表
     */
    public List<Company> findAll(){
        return companyDao.findAll();
    }
}
