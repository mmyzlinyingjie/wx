package com.yy.ent.wx.action;

import com.yy.ent.cherrice.annotation.Read;
import com.yy.ent.cherrice.ret.Render;
import com.yy.ent.commons.base.inject.Inject;
import com.yy.ent.wx.base.BaseAction;
import com.yy.ent.wx.common.model.User;
import com.yy.ent.wx.common.util.MD5;
import com.yy.ent.wx.dao.UserDao;

public class UserAction extends BaseAction{
	
	@Inject(instance = UserDao.class)
	private UserDao userDao;
	
	public Render login(@Read(key = "name") String name, @Read(key = "password") String password) throws Exception{
		
		String message = "登录失败";
	    User user = userDao.query(name);
	     if(user!=null){
	    	 String md5Pass = MD5.GetMD5Code(password+user.getSalt());
	    	 String userPass = user.getPassword();
		     if(md5Pass.equals(userPass)){
		    	 getRequest().setAttribute("user", user);
		    	 message = "登录成功";
		     }
	     }
		 return getRender(message);
	}
}
