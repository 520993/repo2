package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @create 2020-03-04-13:36
 */
@RestController
@CrossOrigin
@RequestMapping("/sys")
public class PermissionController  {

    @Autowired
    private PermissionService permissionService;

    //保存
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }
    //删除
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public  Result delete(@PathVariable(value = "id") String id) throws CommonException {
        permissionService.delete(id);
        return Result.SUCCESS();
    }
    //修改
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
    public  Result update(@PathVariable(value = "id") String id,@RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.update(map);
        return Result.SUCCESS();
    }
    //查询
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public  Result findById(@PathVariable(value = "id") String id) throws CommonException {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }
    //查询全部
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public  Result findAll(@RequestParam Map map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }
}
