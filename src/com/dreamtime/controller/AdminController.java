package com.dreamtime.controller;

import com.dreamtime.model.admin.UserInfoModel;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.dreamtime.model.admin.LogModel;
import com.dreamtime.interceptor.AdminInterceptor;


@Before(AdminInterceptor.class)
public class AdminController extends Controller {
	/**
	 * 	标题: Login
	 *  功能：登陆系统
	 * 	编写时间: 2019年5月9日
	 * 	作者: 李颖鹏
	 */
	@Clear(AdminInterceptor.class)
	public void Login() {
		String username = getPara("username");
		String password = getPara("password");
		UserInfoModel md = new UserInfoModel().getByUsername(username);
		int status = 1;
		String un = "";
		if(md != null) {
			if(md.getPassword().equals(password)) {
				setAttr("result", 0);
				setCookie("cname",md.getUsername(), 36000);
				setSessionAttr("user", md);
				status = 0;
				un = md.getUsername();
			}
			else {
				setAttr("result", 1);
			}
		}
		else {
			setAttr("result", 2);
		}
		LogModel.saveLog(un, status, "PC端",getRequest());
		renderJson();
	}
	/**
	 * 	标题: outLogin
	 *  功能：退出系统
	 * 	编写时间: 2019年5月9日
	 * 	作者: 李颖鹏
	 */
	@Clear(AdminInterceptor.class)
	public void outLogin() {
		removeCookie("username");
		removeSessionAttr("user");
		redirect("/admin");
	}
	/**
	 * 标题: index
	 * 功能: 主页
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void index() {
		setAttr("user", getSessionAttr("user"));
		render("index.html");
	}
	
	/**
	 * 标题: main
	 * 功能: 首页
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void main() {
		render("main.html");
	}
	/**
	 * 标题: updatePassword
	 * 功能: 修改用户密码
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void updatePassword() {
		String id = getPara("id");
		String password = getPara("password");
		boolean result = UserInfoModel.updatePassword(id, password);
		setAttr("result", result);
		removeCookie("usename");
		removeSessionAttr("user");
		renderJson();
	}
	/**
	 * 标题: openUserInfo
	 * 功能: 打开用户信息列表页面
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void openUserInfo() {
		setAttr("user", getSessionAttr("user"));
		render("UserInfo/userinfoInfo.html");
	}
	/**
	 * 标题: openUserInfoAdd
	 * 功能: 打开增加用户页面
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void openUserInfoAdd() {
		render("UserInfo/userinfoAdd.html");
	}
	/**
	 * 标题: openUserInfoEdit
	 * 功能: 打开修改用户页面
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void openUserInfoEdit() {
		render("UserInfo/userinfoEdit.html");
	}
	/**
	 * 标题: openUserInfoDetail
	 * 功能: 打开用户个人信息页面
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void openUserInfoDetail() {
		render("UserInfo/userinfoDetail.html");
	}
	/**
	 * 标题: openUpwd
	 * 功能: 打开用户修改密码页面
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void openUpwd() {
		render("UserInfo/uppassword.html");
	}
	/**
	 * 标题: queryUserInfo
	 * 功能: 获取用户信息列表
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void queryUserInfo() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<UserInfoModel> list = UserInfoModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}
	/**
	 * 标题: saveUserInfo
	 * 功能: 保存用户信息
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void saveUserInfo() {
		String id = getPara("id");
		String username = getPara("username");
		String password = getPara("password");
		int status = getParaToInt("status");
		String time = getPara("time");
		int user_type = getParaToInt("user_type");
		String user_id = getPara("user_id");
		boolean result = UserInfoModel.save(id, username, password, time, user_type, user_id);
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 标题: updateUserInfo
	 * 功能: 更新用户信息
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void updateUserInfo() {
		String id = getPara("id");
		String username = getPara("username");
		String password = getPara("password");
		int status = getParaToInt("status");
		String time = getPara("time");
		int user_type = getParaToInt("user_type");
		String user_id = getPara("user_id");
		boolean result = UserInfoModel.update(id, username, password, time, user_type, user_id);
		setAttr("result", result);
		renderJson();
	}
	/**
	 * 标题: delUserInfo
	 * 功能: 删除用户信息
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public void delUserInfo() {
		String id = getPara("id");
		// 删除
		boolean result = UserInfoModel.delUserInfoById(id);
		// 返回结果
		setAttr("result", result);
		renderJson();
	}
	
	/**
	 *  标题: loginLog
	 * 	功能: 打开日志管理界面
	 * 	编写时间: 2019年5月10日
	 * 	作者: 李颖鹏
	 */
	public void loginLog() {
		render("login/loginfo.html");
	}
	/**
	 *  标题: loginLog
	 * 	功能: 获取日志信息列表
	 * 	编写时间: 2019年5月10日
	 * 	作者: 李颖鹏
	 */
	public void queryloginLog() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<LogModel> list = LogModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "你好！");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}
	/**
	 *  标题: loginLog
	 * 	功能: 删除日志信息信息
	 * 	编写时间: 2019年5月10日
	 * 	作者: 李颖鹏
	 */
	public void delloginLog() {
		String id = getPara("id");
		boolean result = LogModel.delLoginLogByID(id);
		setAttr("result", result);
		renderJson();
	}
}
