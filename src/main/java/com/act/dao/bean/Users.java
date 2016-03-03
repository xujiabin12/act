package com.act.dao.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String userid; //用户ID
	
	private String username; //用户名
	
	private String nickname; //昵称
	
	private String password;
	
	private String role; //0管理员  1老师  2学生
	
	private String auth;
	
	private String realname;
	
	private String mgrpwd;
	
	private String state;
	
	private String openid;
	
	private String sex; //1   男  2女   0未知
	
	private String headimg; //头像
	
	private Date createdt;
	
	
	public Users(){}
	
	public Users(String userid,String username,String nickname,String password){
		this.userid = userid;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
	}
	
	
	
	
	

	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	 

	public String getMgrpwd() {
		return mgrpwd;
	}

	public void setMgrpwd(String mgrpwd) {
		this.mgrpwd = mgrpwd;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	

}
