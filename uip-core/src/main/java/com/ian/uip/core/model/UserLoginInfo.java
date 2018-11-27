package com.ian.uip.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLoginInfo implements Serializable {

    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * token
     */
    private String token;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色ID列表
     */
    private List<String> roleIdList;

    /**
     * 角色资源列表
     */
    private List<String> permissionIdList;

}
