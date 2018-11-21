package com.ian.uip.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 用户名
     */
	private String username;
    /**
     * 密码
     */
	private String password;
    /**
     * 盐
     */
	private String salt;
    /**
     * 邮箱
     */
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
	@TableField(exist=false)
	private String deptName;
	
	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;
	
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
	
	/**
	 * 创建者ID
	 */
	@TableField(exist=false)
	private Long createUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysUser{" +
			", id=" + id +
			", username=" + username +
			", password=" + password +
			", salt=" + salt +
			", email=" + email +
			", mobile=" + mobile +
			", status=" + status +
			", deptId=" + deptId +
			", createTime=" + createTime +
			"}";
	}
}
