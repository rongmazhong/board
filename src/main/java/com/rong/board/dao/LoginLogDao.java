package com.rong.board.dao;

import com.rong.board.domain.LoginLog;
import org.springframework.stereotype.Repository;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:08 17.11.9
 * Package: com.rong.board.dao
 */
@Repository
public class LoginLogDao extends BaseDao<LoginLog> {
	public void save(LoginLog loginLog){
		this.getHibernateTemplate().save(loginLog);
	}
}
