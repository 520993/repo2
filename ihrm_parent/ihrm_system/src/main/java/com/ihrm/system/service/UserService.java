package com.ihrm.system.service;

import com.ihrm.common.util.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author xf
 * @create 2020-03-02-19:29
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleDao roleDao;

    //保存
    public void save(User user){
        String id = idWorker.nextId() + "";
        user.setPassword("123456"); //设置初始密码
        user.setEnableState(1);
        user.setId(id);
        userDao.save(user);
    }

    //删除
    public void delete(String id){
        userDao.deleteById(id);
    }

    //更新
    public  void update(User user){
        User target =userDao.findById(user.getId()).get();
        target.setUsername(user.getUsername());
        target.setPassword(user.getPassword());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        userDao.save(target);
    }
    //查询
    public Page findAll(Map<String,Object> map,int page,int size){
        //1.需要查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //判断根据请求companyId对否空构造查询
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                //判断根据请求departmentId对否空构造查询
                if (!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                if(!StringUtils.isEmpty(map.get("hasDept"))){
                    //根据请求hasDept判断  是否分配部门 0 未分配(departmentId=null)  1已分配(departmentId!=null)
                    if ("0".equals((String)map.get("hasDept"))){
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    }else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }
                //根据请求hasDept判断  是否分配部门 0 未分配(departmentId=null)  1已分配(departmentId!=null)

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //2.构建分页
        Page<User> pageUser = userDao.findAll(spec, new PageRequest(page-1, size));
        return pageUser;

    }
    //根据id查询
    public User findById(String id){
        User user = userDao.findById(id).get();
        return user;
    }

    //分配角色
    public void assignRoles(String userId, List<String> roleList) {
        //根据id查询用户
        User user = userDao.findById(userId).get();

        //设置用户角色集合
        Set<Role> roleSet = new HashSet<>();
        for (String roleId : roleList) {
            Role role = roleDao.findById(roleId).get();
            roleSet.add(role);
        }
        //设置关系
        user.setRoles(roleSet);
        //更新用户集合
        userDao.save(user);
    }
    //根据mobile查询用户
    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }
}
