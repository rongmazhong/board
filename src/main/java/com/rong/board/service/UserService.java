package com.rong.board.service;

import com.rong.board.dao.LoginLogDao;
import com.rong.board.dao.UserDao;
import com.rong.board.domain.LoginLog;
import com.rong.board.domain.User;
import com.rong.board.exceptions.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:27 17.11.9
 * Package: com.rong.board.service
 */
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginLogDao loginLogDao;

	/**
	 * 注册新用户，若存在，抛出异常。
	 */
	public void register(User user) throws UserExistException {
		User user1 = userDao.getUserByUserName(user.getUserName());
		if (user1 != null){
			throw new UserExistException("用户名已存在，换一个");
		}else {
			user.setCredit(100);
			user.setUserType(1);
			userDao.save(user);
		}
	}
	/**
	 * 更新用户
	 */
	public void update(User user){
		userDao.update(user);
	}
	/**
	 * 根据用户名、密码查询user对象
	 */
	public User getUserByUserName(String username){
		return userDao.getUserByUserName(username);
	}
	/**
	 * 加载user对象
	 */
	public User getUserById(int userId){
		return userDao.get(userId);
	}
	/**
	 * 锁定用户
	 */
	public void luckUser(String userName){
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_LOCK);
	}
	/**
	 * 解除锁定
	 */
	public void unLuckUser(String userName){
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.update(user);
	}

	/**
	 * 模糊查询用户
	 */
	public List<User> queryUserByUserName(String userName){
		return userDao.queryUserByUserName(userName);
	}
	/**
	 * 获取所有用户
	 */
	public List<User> getAllUser(){
		return userDao.loadAll();
	}
	/**
	 * 用户登录
	 */
	public void loginSuccess(User user){
		user.setCredit(5+user.getCredit());
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(new Date());
		userDao.update(user);
		loginLogDao.save(loginLog);
	}
}
