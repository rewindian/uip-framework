package com.ian.uip.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ian.uip.core.validator.group.AddGroup;
import com.ian.uip.core.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 */
@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 用户名
     */
    @NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;
    /**
     * 密码
     */
    @NotBlank(message="密码不能为空", groups = AddGroup.class)
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 邮箱
     */
    @Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建者ID
     */
    @TableField(exist = false)
    private Long createUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
