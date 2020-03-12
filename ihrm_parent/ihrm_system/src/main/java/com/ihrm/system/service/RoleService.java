package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.util.IdWorker;
import com.ihrm.common.util.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xf
 * @create 2020-03-03-17:48
 */
@Service
public class RoleService extends BaseService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PermissionDao permissionDao;

    //保存新角色
    public void save(Role role){
        //设置基本信息
        String id = idWorker.nextId() + "";
        role.setId(id);
        roleDao.save(role);
    }

    //删除角色
    public void delete(String id){
        roleDao.deleteById(id);
    }
    //更新
    public void update(Role role){
        Role target = roleDao.findById(role.getId()).get();
        target.setName(role.getName());
        target.setDescription(role.getDescription());
        roleDao.save(target);
    }
    //查询所有
    public List<Role> findAll(){
        return  roleDao.findAll();
    }
    //根据id查询
    public Role findById(String id){
        return  roleDao.findById(id).get();
    }
    //带分页查询
    public Page<Role> findPage(String companyId,int page,int size){
        return roleDao.findAll(getSpec(companyId),PageRequest.of(page-1,size));
    }

    //分配权限
    public void assignPerms(String roleId, List<String> permIds) {
        Role role = roleDao.findById(roleId).get();

        Set<Permission> permissionSet = new HashSet<>();

        for (String  perm: permIds) {
            Permission permission = permissionDao.findById(perm).get();
            //需要父id和类型查询api权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());

            permissionSet.addAll(apiList); //自动赋予api权限
            permissionSet.add(permission); //当前菜单或按钮权限

        }
        role.setPermissions(permissionSet);
        roleDao.save(role);
    }
}
