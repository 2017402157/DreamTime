package com.dreamtime.model.admin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.dreamtime.util.StringUtil;

public class UserInfoModel extends Model<UserInfoModel> {

	private static final long serialVersionUID = 1L;
	public static final String tableName = "user_info";
	public static final UserInfoModel dao = new UserInfoModel();
	public String getId() {
		return get("id");
	}
	public void setId(String id) {
		set("id", id);
	}
	public String getUsername() {
		return get("username");
	}
	public void setUsername(String username) {
		set("usrname", username);
	}
	public String getPassword() {
		return get("password");
	}
	public void setPassword(String password) {
		set("password", password);
	}
	public int getStatus() {
		return get("status");
	}
	public void setStatus(int status) {
		set("status", status);
	}
	public Date getTime() {
		return get("time");
	}
	public void setTime(String time) {
		set("time", time);
	}
	public int getUser_type() {
		return get("user_type");
	}
	public void setUser_type(int user_type) {
		set("user_type", user_type);
	}
	public String getUser_id() {
		return get("user_id");
	}
	public void setUser_id(String user_id) {
		set("user_id",user_id);
	}
	
	
	/**
	 * 功能:根据username查找正常用户
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public static UserInfoModel getByUsername(String username){
		return dao.findFirst("select * from " + tableName + " where username = ? and status=0" , username);
	}
	
	/**
	 *  功能：根据id查找正常用户
	 *  编写时间: 2019年5月9日
	 * 	作者: 李颖鹏
	 */
	public static UserInfoModel getByID(String id){
		return dao.findFirst("select * from " + tableName + " where id = ? and status=0" , id);
	}
	/**
	 *  功能：修改密码
	 *  编写时间: 2019年5月9日
	 * 	 作者: 李颖鹏
	 */
	public static boolean updatePassword(String id,String password){
		UserInfoModel m=getById(id);
		m.setPassword(password);
		return m.update();
	}
	
	/**
	 * 分页查询显示，就是查找
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public static Page<UserInfoModel> getList(int pageNumber, int pageSize, String key) {
		String sele_sql = "select * ";
		StringBuffer from_sql = new StringBuffer();
		from_sql.append("from ").append(tableName);
		if (!StringUtil.isBlankOrEmpty(key)) {
			from_sql.append(" where username like '%" + key + "%'");
		}
		from_sql.append("  ORDER BY id ");
		return dao.paginate(pageNumber, pageSize, sele_sql, from_sql.toString());
	}

	/**
	 * 根据id查找
	 * 编写时间: 2019年5月9日
	 * 	作者: 李颖鹏
	 */
	public static UserInfoModel getById(String id) {
		return dao.findFirst("select * from " + tableName + " where id = ? ", id);
	}
	/**
	 *  功能：保存用户信息
	 *  编写时间: 2019年5月9日
	 * 	作者: 李颖鹏
	 */
	public static boolean save(String id, String username, String password, String time, int user_type, String user_id) {
		UserInfoModel model = new UserInfoModel();
		model.setId(id);
		model.setUsername(username);
		model.setPassword(password);
		model.setStatus(0);//默认开启	0:正常，1异常
		model.setTime(time);
		model.setUser_type(user_type);
		model.setUser_id(user_id);
		return model.save();
	}

	/**
	 * 保存，这个保存时事务的保存，关于支付，关于钱的保存，我们一般都用这样的保存方法，现在我们暂时不用这个，因为不好调试
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	@Before(Tx.class)
	public static boolean save(final UserInfoModel use) {
		boolean succeed = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				use.save();
				return true;
			}
		});
		return succeed;
	}

	/**
	 * 更新
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public static boolean update(String id, String username, String password, String time, int user_type,
			String user_id) {
		UserInfoModel model = UserInfoModel.getById(id);
		if (model == null) {
			return false;
		}
		model.setId(id);
		model.setUsername(username);
		model.setPassword(password);
		model.setStatus(0);//默认开启	0:正常，1异常
		model.setTime(time);
		model.setUser_type(user_type);
		model.setUser_id(user_id);
		try {
			model.update();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据学号删除数据
	 * 编写时间: 2019年5月9日
	 * 作者: 李颖鹏
	 */
	public static boolean delUserInfoById(String id) {
		try {
			String delsql = "DELETE FROM " + tableName + " WHERE id=?";
			int iRet = Db.update(delsql, id);
			if (iRet > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	 public static UserInfoModel findModelbyUsername(String username) {
	    	String sql="select * from "+tableName+" where username=?";
	    	return dao.findFirst(sql,username);
	    }
	 /**
		 *  功能：获取班主任的信息列表
		 *  编写时间: 2019年5月9日
		 * 	 作者: 李颖鹏
		 */
	 public static List<UserInfoModel> getHeadmasters(String type) {
	    	String sql="select id,username from "+tableName+"";
	    	return dao.find(sql);
	    }
}
