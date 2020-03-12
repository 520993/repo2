package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @create 2020-03-04-9:44
 */
@RestController
@CrossOrigin
@RequestMapping("/sys")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    //保存新角色
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result save(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    //删除角色
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        roleService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
    //更新
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Role role){
       roleService.update(role);
       return Result.SUCCESS();
    }
    //查询所有
    @RequestMapping(value = "/role/list",method = RequestMethod.GET)
    public Result findAll(){
        List<Role> roles = roleService.findAll();
        return new Result(ResultCode.SUCCESS,roles);
    }
    //查询所有分页
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findByPage(int page, int size, Role role){
        Page<Role> roles = roleService.findPage(companyId, page, size);
        PageResult<Role> rolePageResult = new PageResult<>(roles.getTotalElements(), roles.getContent());
        return new Result(ResultCode.SUCCESS,rolePageResult);
    }
    //根据id查询
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS,role);
    }
    //分配权限
    @RequestMapping(value = "/role/assignPerm",method = RequestMethod.PUT)
    public Result assignPerm(@RequestBody Map<String,Object> map){
        //被分配角色id
        String roleId = (String) map.get("id");
        //权限列表
        List<String> permIds = (List<String>) map.get("permIds");
        //调用service完成逻辑
        roleService.assignPerms(roleId,permIds);
        return Result.SUCCESS();
    }
}
