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
	 * 	����: Login
	 *  ���ܣ���½ϵͳ
	 * 	��дʱ��: 2019��5��9��
	 * 	����: ��ӱ��
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
		LogModel.saveLog(un, status, "PC��",getRequest());
		renderJson();
	}
	/**
	 * 	����: outLogin
	 *  ���ܣ��˳�ϵͳ
	 * 	��дʱ��: 2019��5��9��
	 * 	����: ��ӱ��
	 */
	@Clear(AdminInterceptor.class)
	public void outLogin() {
		removeCookie("username");
		removeSessionAttr("user");
		redirect("/admin");
	}
	/**
	 * ����: index
	 * ����: ��ҳ
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void index() {
		setAttr("user", getSessionAttr("user"));
		render("index.html");
	}
	
	/**
	 * ����: main
	 * ����: ��ҳ
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void main() {
		render("main.html");
	}
	/**
	 * ����: updatePassword
	 * ����: �޸��û�����
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
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
	 * ����: openUserInfo
	 * ����: ���û���Ϣ�б�ҳ��
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void openUserInfo() {
		setAttr("user", getSessionAttr("user"));
		render("UserInfo/userinfoInfo.html");
	}
	/**
	 * ����: openUserInfoAdd
	 * ����: �������û�ҳ��
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void openUserInfoAdd() {
		render("UserInfo/userinfoAdd.html");
	}
	/**
	 * ����: openUserInfoEdit
	 * ����: ���޸��û�ҳ��
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void openUserInfoEdit() {
		render("UserInfo/userinfoEdit.html");
	}
	/**
	 * ����: openUserInfoDetail
	 * ����: ���û�������Ϣҳ��
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void openUserInfoDetail() {
		render("UserInfo/userinfoDetail.html");
	}
	/**
	 * ����: openUpwd
	 * ����: ���û��޸�����ҳ��
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void openUpwd() {
		render("UserInfo/uppassword.html");
	}
	/**
	 * ����: queryUserInfo
	 * ����: ��ȡ�û���Ϣ�б�
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void queryUserInfo() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<UserInfoModel> list = UserInfoModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "��ã�");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}
	/**
	 * ����: saveUserInfo
	 * ����: �����û���Ϣ
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
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
	 * ����: updateUserInfo
	 * ����: �����û���Ϣ
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
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
	 * ����: delUserInfo
	 * ����: ɾ���û���Ϣ
	 * ��дʱ��: 2019��5��9��
	 * ����: ��ӱ��
	 */
	public void delUserInfo() {
		String id = getPara("id");
		// ɾ��
		boolean result = UserInfoModel.delUserInfoById(id);
		// ���ؽ��
		setAttr("result", result);
		renderJson();
	}
	
	/**
	 *  ����: loginLog
	 * 	����: ����־�������
	 * 	��дʱ��: 2019��5��10��
	 * 	����: ��ӱ��
	 */
	public void loginLog() {
		render("login/loginfo.html");
	}
	/**
	 *  ����: loginLog
	 * 	����: ��ȡ��־��Ϣ�б�
	 * 	��дʱ��: 2019��5��10��
	 * 	����: ��ӱ��
	 */
	public void queryloginLog() {
		String key = getPara("key");
		int limit = getParaToInt("limit");
		int page = getParaToInt("page");
		Page<LogModel> list = LogModel.getList(page, limit, key);
		setAttr("code", 0);
		setAttr("msg", "��ã�");
		setAttr("count", list.getTotalRow());
		setAttr("data", list.getList());
		renderJson();
	}
	/**
	 *  ����: loginLog
	 * 	����: ɾ����־��Ϣ��Ϣ
	 * 	��дʱ��: 2019��5��10��
	 * 	����: ��ӱ��
	 */
	public void delloginLog() {
		String id = getPara("id");
		boolean result = LogModel.delLoginLogByID(id);
		setAttr("result", result);
		renderJson();
	}
}
