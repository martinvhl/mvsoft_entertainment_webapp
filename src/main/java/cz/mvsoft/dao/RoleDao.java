package cz.mvsoft.dao;

import cz.mvsoft.entity.users.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
