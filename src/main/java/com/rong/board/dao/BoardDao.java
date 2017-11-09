package com.rong.board.dao;

import com.rong.board.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:00 17.11.9
 * Package: com.rong.board.dao
 */
@Repository
public class BoardDao extends BaseDao<Board> {
	private static final String GET_BOARD_NUM = "select count(f.boardId) from Board f";
	public long getBoardNum(){
		Iterator iterator = getHibernateTemplate().iterate(GET_BOARD_NUM);
		return (long) iterator.next();
	}
}
