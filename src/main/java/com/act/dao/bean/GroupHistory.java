package com.act.dao.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="grouphistory")
public class GroupHistory  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String historyId; 
	
	private String groupId;
	
	private String nickName;
	
	private String userName;
	
	private String headimg;
	
	private String role;
	
	private String message;
	
	private Date createdt;
	
	public GroupHistory(){}
	
	public GroupHistory(String groupId,String nickName,String userName,String headimg,String role,String message){
		this.groupId = groupId;
		this.nickName = nickName;
		this.userName = userName;
		this.headimg = headimg;
		this.role = role;
		this.message = message;
		this.createdt = new Date();
	}
	

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}
	
	
	
	

}
