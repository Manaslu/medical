/**
 * 
 */
package com.teamsun.framework.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DingLin
 *
 */
public class Certification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5708749345414567198L;

	public Integer oid;
    
    public String logonName;
    
    public String password;
    
    public List userGroupList;
    
    public Certification(){
        userGroupList = new ArrayList();
    }
    
    public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List userGroupList) {
		this.userGroupList = userGroupList;
	}

	public void addUserGroup(Object o){
        userGroupList.add(o);
    }
    public void removeUserGroup(Object o){
        userGroupList.remove(o);
    }

}
