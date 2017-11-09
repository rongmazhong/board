package com.rong.board.web;

import com.rong.board.domain.User;
import com.rong.board.exceptions.UserExistException;
import com.rong.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:rong
 * Description:用户注册
 * Data: Create in 下午 4:52 17.11.9
 * Package: com.rong.board.web
 */
@Controller
public class RegisterController extends BaseController {
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, User user){
		ModelAndView view = new ModelAndView();
		view.setViewName("/success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			view.addObject("errorMsg","用户已存在，请换一个名字");
			view.setViewName("forward:/register");
		}
		setSessionUser(request,user);
		return view;
	}
}
