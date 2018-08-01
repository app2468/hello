package cn.itcast.nsfw.user.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
	
	private UserRoleId id;

	public UserRole() {
		super();
	}

	public UserRole(UserRoleId id) {
		super();
		this.id = id;
	}

	public UserRoleId getId() {
		return id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}
}
