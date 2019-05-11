package com.dreamtime.config;

import com.dreamtime.controller.AdminController;
import com.dreamtime.controller.WeixinController;
import com.dreamtime.model.admin.LogModel;
import com.dreamtime.model.admin.UserInfoModel;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("DevMode", true));
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/admin", AdminController.class,"WEB-INF/admin");
		me.add("/wudi", WeixinController.class);
		
	}

	@Override
	public void configEngine(Engine me) {
		
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// ≤Â»Î ‰»Îø‚≤Âº˛
		DruidPlugin dsMysql = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"),
				getProperty("password").trim());
		{
			dsMysql.setTestOnBorrow(true);
			dsMysql.setTestOnReturn(true);
			dsMysql.setMaxWait(20000);
		}
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin("mysql", dsMysql);
		boolean showSql = getPropertyToBoolean("showSql", true);
		arpMysql.setShowSql(showSql);
		{
			arpMysql.addMapping("user_info", UserInfoModel.class);
			arpMysql.addMapping("login_log", LogModel.class);
		}
		me.add(dsMysql);
		me.add(arpMysql);

	}

	@Override
	public void configInterceptor(Interceptors me) {
		
		
	}

	@Override
	public void configHandler(Handlers me) {
		
		
	}

}
