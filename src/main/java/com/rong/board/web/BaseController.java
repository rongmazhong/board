package com.rong.board.web;

import com.rong.board.domain.CommonConstant;
import com.rong.board.domain.User;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:rong
 * Description:controller的父类
 * Data: Create in 下午 12:53 17.11.9
 * Package: com.rong.board.web
 */
public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";
	/**
	 * 获取session中的用户
	 */
	protected User getSessionUser(HttpServletRequest request){
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	/**
	 * 保存用户到session中
	 */
	protected void setSessionUser(HttpServletRequest request,User user){
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT,user);
	}
	/**
	 * 获取程序的URL绝对路径
	 */
	public final String getAppbaseUrl(HttpServletRequest request,String url){
		Assert.hasLength(url,"url不能为空");
		Assert.isTrue(url.startsWith("/"),"必须以/打头");
		return request.getContextPath()+url;
	}
}
