package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.util.BeanMapUtils;
import com.ihrm.common.util.IdWorker;
import com.ihrm.common.util.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xf
 * @create 2020-03-02-19:29
 */
@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMenuDao permissionMenuDao;
    @Autowired
    private PermissionPointDao permissionPointDao;
    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;


    //保存(修改)
    public void save(Map<String,Object> map) throws Exception {
        String id = idWorker.nextId() + "";
        //通过map去构造权限对象
        Permission perm = BeanMapUtils.ToBean(Permission.class,map);
        perm.setId(id);
        //根据不同资源类型构造不同对象
        int type = perm.getType();
        switch (type){
            case PermissionConstants.PY_MENU ://构造menu对象
                PermissionMenu menu =  BeanMapUtils.ToBean(PermissionMenu.class,map);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT :
                PermissionPoint point =  BeanMapUtils.ToBean(PermissionPoint.class,map);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API :
                PermissionApi api =  BeanMapUtils.ToBean(PermissionApi.class,map);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw  new CommonException(ResultCode.FAIL);
        }
        //保存权限
        permissionDao.save(perm);

    }

    //删除
    public void delete(String id) throws CommonException {
        //删除权限
        //删除权限对应的资源
        Permission perm = permissionDao.findById(id).get();
        permissionDao.delete(perm);
        //构造不同资源
        int type = perm.getType();
        switch (type){
            case PermissionConstants.PY_MENU :
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.PY_POINT :
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.PY_API :
                permissionApiDao.deleteById(id);
                break;
            default:
                throw  new CommonException(ResultCode.FAIL);
        }
    }

    //更新(修改)
    public  void update(Map<String,Object> map) throws Exception {
        Permission perm = BeanMapUtils.ToBean(Permission.class,map);
        Permission permission = permissionDao.findById(perm.getId()).get();
        permission.setCode(perm.getCode());
        permission.setName(perm.getName());
        permission.setEnVisible(perm.getEnVisible());
        permission.setDescription(perm.getDescription());
        //构造不同资源
        int type = perm.getType();
        switch (type){
            case PermissionConstants.PY_MENU :
                PermissionMenu menu = BeanMapUtils.ToBean(PermissionMenu.class,map);
                menu.setId(perm.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.PY_POINT :
                PermissionPoint point =  BeanMapUtils.ToBean(PermissionPoint.class,map);
                point.setId(perm.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.PY_API :
                PermissionApi api =  BeanMapUtils.ToBean(PermissionApi.class,map);
                api.setId(perm.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw  new CommonException(ResultCode.FAIL);
        }
        permissionDao.save(permission);
    }
    //查询
    public List<Permission> findAll(Map<String, Object> map){
        //1.需要查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //根据父id查询
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("pid"))){
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class),(String)map.get("pid")));
                }
                //根据enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class),(String)map.get("enVisible")));
                }
                //根据 type查询
                if(!StringUtils.isEmpty(map.get("type"))){
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    if ("0".equals(ty)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(ty));
                    }
                }
                //根据请求hasDept判断  是否分配部门 0 未分配(departmentId=null)  1已分配(departmentId!=null)

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }
    //根据id查询
    public Map<String,Object> findById(String id) throws CommonException {
        Permission perm = permissionDao.findById(id).get();
        int type = perm.getType();
        //根据类型构造不同对象
        Object obj = null;
        if (type == PermissionConstants.PY_MENU){
            obj = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.PY_POINT){
            obj = permissionPointDao.findById(id).get();
        }else if(type == PermissionConstants.PY_API){
            obj = permissionApiDao.findById(id).get();
        }else {
            throw  new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(obj);
        map.put("name",perm.getName());
        map.put("type",perm.getType());
        map.put("code",perm.getCode());
        map.put("description",perm.getDescription());
        map.put("pid",perm.getPid());
        map.put("enVisible",perm.getEnVisible());
        return map;
    }

}
