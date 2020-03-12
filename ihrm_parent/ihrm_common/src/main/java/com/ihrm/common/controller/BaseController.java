package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xf
 * @create 2020-03-01-15:54
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    @ModelAttribute        //在进入控制器方法之前执行的内容
    public void setResAndReq(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
        /**
         * 目前使用企业id=1,企业名称=MS教育有限公司
         */
        this.companyId = "1";
        this.companyName = "MS教育有限公司";
    }
}
