package com.act.dao.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.act.util.DateUtils;

@Entity
@Table(name="grouphistory")
public class GroupHistory  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String historyid; 
	
	private String groupid;
	
	private String nickname;
	
	private String username;
	
	private String headimg;
	
	private String role;
	
	private String message;
	
	private Date createdt;
	
	@Transient
	private String createdtStr;
	
	public GroupHistory(){}
	
	public GroupHistory(String historyId,String groupId,String nickName,String userName,String headimg,String role,String message){
		this.historyid = historyId;
		this.groupid = groupId;
		this.nickname = nickName;
		this.username = userName;
		this.headimg = headimg;
		this.role = role;
		this.message = message;
		this.createdt = new Date();
	}

	public String getHistoryid() {
		return historyid;
	}

	public void setHistoryid(String historyid) {
		this.historyid = historyid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCreatedtStr() {
		createdtStr = DateUtils.formatDatetime(this.createdt);
		return createdtStr;
	}

	public void setCreatedtStr(String createdtStr) {
		this.createdtStr = createdtStr;
	}
	

	 
	
	

}
