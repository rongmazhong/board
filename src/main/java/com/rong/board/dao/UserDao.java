package com.rong.board.dao;

import com.rong.board.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author:rong
 * Description:
 * Data: Create in 下午 5:48 17.11.8
 * Package: com.rong.board.dao
 */
@Repository
public class UserDao extends BaseDao<User> {
	private static final String GET_USER_BY_USERNAME = "from User u where u.userName = ?";

	private static final String QUERY_USER_BY_USERNAME = "from User u where u.userName like ?";

	/**
	 * 根据名字查询用户
	 */
	public User getUserByUserName(String userName) {
		List<User> users = (List<User>) getHibernateTemplate().find(GET_USER_BY_USERNAME, userName);
		if (users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}
		/**
		 * 根据用户名为模糊查询条件，查询出所有前缀匹配的User对象
		 * @param userName 用户名查询条件
		 * @return 用户名前缀匹配的所有User对象
		 */
		public List<User> queryUserByUserName(String userName){
			return (List<User>)getHibernateTemplate().find(QUERY_USER_BY_USERNAME,userName+"%");
		}
}
