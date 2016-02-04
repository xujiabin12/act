package com.act.dao.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="groups")
public class Groups implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String groupid;
	
	private String groupname;
	
	private String groupdesc;
	
	private String owner;
	
	private String state;
	
	
	
	public Groups(){
		
	}
	
	public Groups(String groupid,String groupname,String groupdesc,String owner){
		this.groupdesc = groupdesc;
		this.groupname = groupname;
		this.owner = owner;
		this.groupid = groupid;
	}
	
	
	

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupdesc() {
		return groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
	

}
