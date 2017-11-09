package com.rong.board.web;

import com.rong.board.domain.CommonConstant;
import com.rong.board.domain.User;
import com.rong.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Author:rong
 * Description:登录控制
 * Data: Create in 下午 5:22 17.11.9
 * Package: com.rong.board.web
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private UserService userService;
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, User user){
		User dbUser = userService.getUserByUserName(user.getUserName());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/login");
		if (dbUser == null){
			modelAndView.addObject("errorMsg","用户不存在");
		}else if (!dbUser.getPassword().equals(user.getPassword())){
			modelAndView.addObject("errorMsg","密码错误");
		}else if (dbUser.getLocked() == User.USER_LOCK){
			modelAndView.addObject("errorMsg","用户已被锁定，禁止登录");
		}else {
			dbUser.setLastIp(request.getRemoteAddr());
			dbUser.setLastVisit(new Date());
			userService.loginSuccess(dbUser);
			setSessionUser(request,dbUser);
			String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			//如果当前回话中没有保存之前的请求URL，直接跳转到主页
			if (org.springframework.util.StringUtils.isEmpty(toUrl)){
				toUrl = "/index.html";
			}
			modelAndView.setViewName("redirect:"+toUrl);
		}
		return modelAndView;
	}

	public String logout(HttpSession session){
		session.removeAttribute(CommonConstant.USER_CONTEXT);
		return "forward:/index";
	}
}
