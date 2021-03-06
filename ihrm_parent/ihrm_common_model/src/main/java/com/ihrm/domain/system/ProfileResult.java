package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xf
 * @create 2020-03-06-14:31
 */
@Setter
@Getter
public class ProfileResult implements Serializable {
    private String mobile;
    private String username;
    private String company;
    private Map<String,Object> roles = new HashMap<>();
    public ProfileResult(User user){
        this.mobile = user.getMobile();
        this.username=user.getUsername();
        this.company = user.getCompanyName();
        Set<Role> roles = user.getRoles();

        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Role role : roles) {
            Set<Permission> perms = role.getPermissions();
            for (Permission perm : perms) {
                String code = perm.getCode();
                if (perm.getType() == 1){
                    menus.add(code);
                }else if (perm.getType() == 2){
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
       this.roles.put("menus",menus);
       this.roles.put("points",points);
       this.roles.put("apis",apis);
    }
}
