package com.dreamtime.model.admin;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class StudentModel extends Model<StudentModel> {
	private static final long serialVersionUID = 1L;
	public static final String tableName = "student";
	public static final StudentModel dao = new StudentModel();
	
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getUsername() {
		return getSql("id");
	}
	public void setUsername(String username) {
		set("username", username);
	}
	public String getStu_id() {
		return get("stu_id");
	}
	public void setStu_id(String stu_id) {
		set("stu_id", stu_id);
	}
	public int getSex() {
		return get("sex");
	}
	public void setSex(String sex) {
		set("sex", sex);
	}
	public Date getBirth() {
		return get("birth");
	}
	public void setBirth(String birth) {
		set("birth", birth);
	}
	public int getStu_type() {
		return get("stu_type");
	}
	public void setStu_type(String stu_type) {
		set("stu_type",stu_type);
	}
}
