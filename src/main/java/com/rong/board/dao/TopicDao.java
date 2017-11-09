package com.rong.board.dao;

import com.rong.board.domain.Page;
import com.rong.board.domain.Topic;
import org.springframework.stereotype.Repository;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:10 17.11.9
 * Package: com.rong.board.dao
 */
@Repository
public class TopicDao extends BaseDao<Topic> {
	private static final String GET_BOARD_DIGEST_TOPICS = "from Topic t " +
			"where t.boardId = ? and digest > 0 order by t.lastPost desc, digest desc";
	private static final String GET_PAGED_TOPICS = "from Topic " +
			"where boardId=? order by lastPost desc";
	private static final String QUERY_TOPIC_BY_TITILE ="from Topic " +
			"where topicTitle like ? order by lastPost desc";
	/**
	 * 获取论坛版块某一页的精华主题帖，按最后回复时间及精华级别降序排序
	 */
	public Page getBoardDigestTopics(int boardId,int pageNo,int pageSize){
		return pagedQuery(GET_BOARD_DIGEST_TOPICS,pageNo,pageSize,boardId);
	}
	/**
	 * 获取论坛版块分页的主题帖子
	 */
	public Page getPagedTopics(int boardId,int pageNo,int pageSize){
		return pagedQuery(GET_PAGED_TOPICS,pageNo,pageSize,boardId);
	}
	/**
	 * 模糊查询主题帖
	 */
	public Page queryTopicByTitle(String title,int pageNo,int pageSize){
		return pagedQuery(QUERY_TOPIC_BY_TITILE,pageNo,pageSize,title);
	}
}
