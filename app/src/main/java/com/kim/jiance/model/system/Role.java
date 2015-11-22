package com.kim.jiance.model.system;

// Generated 2015-5-7 9:42:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * 角色
 */

public class Role {

	private String id;//标识
	private String createUserId;//创建用户
	private String roleName;//角色名称
	private String roleDescription;//描述
	private Date roleCreateTime;//创建时间
	
//	private Set<UserInfo> users = new HashSet<UserInfo>();//
//	
//	private Set<SysMenu> sysMenus = new HashSet<SysMenu>();//菜单
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public UserInfo getCreateUser() {
//		return CreateUser;
//	}
//	public void setCreateUser(UserInfo createUser) {
//		CreateUser = createUser;
//	}


	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public Date getRoleCreateTime() {
		return roleCreateTime;
	}
	public void setRoleCreateTime(Date roleCreateTime) {
		this.roleCreateTime = roleCreateTime;
	}
//	public Set<SysMenu> getSysMenus() {
//		return sysMenus;
//	}
//	public void setSysMenus(Set<SysMenu> sysMenus) {
//		this.sysMenus = sysMenus;
//	}
//	public Set<UserInfo> getUsers() {
//		return users;
//	}
//	public void setUsers(Set<UserInfo> users) {
//		this.users = users;
//	}
//	
}
