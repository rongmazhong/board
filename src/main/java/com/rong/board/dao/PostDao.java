package com.rong.board.dao;

import com.rong.board.domain.Page;
import com.rong.board.domain.Post;
import org.springframework.stereotype.Repository;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:23 17.11.9
 * Package: com.rong.board.dao
 */
@Repository
public class PostDao extends BaseDao<Post> {
	private static final String GET_PAGED_POSTS = "from Post " +
			"where topic.topicId =? order by createTime desc";

	private static final String DELETE_TOPIC_POSTS = "delete from Post " +
			"where topic.topicId=?";

	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return pagedQuery(GET_PAGED_POSTS,pageNo,pageSize,topicId);
	}

	/**
	 * 删除主题帖下的所有帖子
	 */
	public void deleteTopicPosts(int topicId){
		getHibernateTemplate().bulkUpdate(DELETE_TOPIC_POSTS,topicId);
	}
}
