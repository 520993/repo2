package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.util.JwtUtils;
import com.ihrm.domain.system.ProfileResult;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @create 2020-03-03-14:16
 */
@RestController
@CrossOrigin
@RequestMapping("/sys")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    //分配角色
    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map){
        //获取被分配权限id
         String userId = (String) map.get("id");
         //获取角色的id列表
        List<String> roleList = (List<String>) map.get("roleIds");
        //
        userService.assignRoles(userId,roleList);
        return new Result(ResultCode.SUCCESS);
    }



    //保存
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){
        //设置用户基础信息
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }
    //查询全部
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map){
        //获取当前企业id
        map.put("companyId",companyId);
        //完成查询
        Page<User> pageUser = userService.findAll(map, page, size);
        //构造返回结果
        PageResult pageResult = new PageResult(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }


    //根据id查询
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        //添加一个新属性(roleIds:已经具有的角色)

        User user = userService.findById(id);
        if (user.getRoleIds()==null || user.getRoleIds().length()<=0){
            user.setRoleIds("123");
        }
//        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS,user);
    }
    //修改
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id ,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }
    //删除
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        userService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    //用户登陆
    /**
     *
     * @param
     * @return
     * 通过mobile查询用户比较密码,生成jwt信息
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);
        if (user==null || !user.getPassword().equals(password)){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            //登陆成功
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",user.getCompanyId());
            map.put("companyName",user.getCompanyName());
            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);
        }
    }

    //用户登陆成功后获取用户信息

    /**
     * 获取用户id
     * 根据id查询用户
     * 构建返回对象
     * 响应
     * @return
     */
    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {
        /**
         * 从请求头信息中获取token数据
         * 1.获取请求头信息,名称=Authorization
         * 2.替换Bearer + 空格
         * 3.解析token获取claims
         */
        //1.获取请求头信息,名称=Authorization
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            throw  new CommonException(ResultCode.UNAUTHENTICATED);
        }
        //2.替换Bearer + 空格
        String token = authorization.replace("Bearer ","");
        //3.解析token获取claims
        Claims claims = jwtUtils.parseJwt(token);

        String userid = claims.getId();
        User user = userService.findById(userid);
        return new Result(ResultCode.SUCCESS,new ProfileResult(user));
     }
}
