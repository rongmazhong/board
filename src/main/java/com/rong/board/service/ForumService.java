package com.rong.board.service;

import com.rong.board.dao.BoardDao;
import com.rong.board.dao.PostDao;
import com.rong.board.dao.TopicDao;
import com.rong.board.dao.UserDao;
import com.rong.board.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:rong
 * Description:
 * Data: Create in 上午 11:52 17.11.9
 * Package: com.rong.board.service
 */
@Service
public class ForumService {
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private PostDao postDao;

	/**
	 * 创建一个版块
	 */
	public void addBoard(Board board){
		boardDao.save(board);
	}
	/**
	 * 删除一个版块
	 */
	public void removeBoard(int boarId){
		Board board = boardDao.get(boarId);
		boardDao.remove(board);
	}
	/**
	 * 将帖子置顶为精华帖
	 * 将帖子创建者给1000积分
	 */
	public void makeDigestTopic(int topicId){
		Topic topic = topicDao.get(topicId);
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = topic.getUser();
		user.setCredit(user.getCredit()+1000);
	}

	/**
	 * 获取所有论坛版块
	 */
	public List<Board> getAllBoards(){
		return boardDao.loadAll();
	}
	/**
	 * 发表一个主题帖，用户+10积分，论坛版块主题帖数量+1
	 */
	public void addTopic(Topic topic){
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum()+1);
		topicDao.save(topic);
		topic.getMainPost().setTopic(topic);
		MainPost mainPost = topic.getMainPost();
		mainPost.setCreateTime(new Date());
		mainPost.setUser(topic.getUser());
		mainPost.setPostTitle(topic.getTopicTitle());
		mainPost.setBoardId(topic.getBoardId());
		postDao.save(topic.getMainPost());
		User user = topic.getUser();
		user.setCredit(user.getCredit()+10);
		userDao.update(user);
	}
	/**
	 * 删除一个主题帖，用户积分-50，论坛版块主题帖数-1，删除主题帖关联的帖子
	 */
	public void removeTopic(int topicId){
		Topic topic = topicDao.get(topicId);
		//主题帖数-1
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum()-1);
		//扣除用户50积分
		User user = topic.getUser();
		user.setCredit(user.getCredit()-50);
		userDao.update(user);
		//删除主题帖下的关联帖
		topicDao.remove(topic);
		postDao.deleteTopicPosts(topicId);
	}
	/**
	 * 添加一个回复贴，用户积分+5，主题帖回复数+1并更新最后回复时间
	 */
	public void addPost(Post post){
		postDao.save(post);
		User user = post.getUser();
		user.setCredit(user.getCredit()+5);
		userDao.update(user);

		Topic topic = post.getTopic();
		topic.setReplies(topic.getReplies()+1);
		topic.setLastPost(new Date());
	}
	/**
	 * 删除一个回复贴，发表回复的用户-20积分，主题帖的回复数-1
	 */
	public void removePost(int postId){
		Post post = postDao.get(postId);
		postDao.remove(post);
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setReplies(topic.getReplies()-1);
		User user = post.getUser();
		user.setCredit(user.getCredit()-20);
		userDao.update(user);
	}
	/**
	 * 获取论坛某页主题帖，以最后回复时间降序排序
	 */
	public Page getPagedTopics(int boardId,int pageNo,int pageSize){
		return topicDao.getPagedTopics(boardId,pageNo,pageSize);
	}
	/**
	 * 获取同一主题帖每页，以最后回复时间降序排序
	 */
	public Page getPagePosts(int topicId,int pageNo,int pageSize){
		return postDao.getPagedPosts(topicId,pageNo,pageSize);
	}
	/**
	 * 查找出所有包括标题包含title的主题帖
	 *
	 * @param title
	 *            标题查询条件
	 * @return 标题包含title的主题帖
	 */
	public Page queryTopicByTitle(String title,int pageNo,int pageSize) {
		return topicDao.queryTopicByTitle(title,pageNo,pageSize);
	}

	/**
	 * 根据boardId获取Board对象
	 *
	 * @param boardId
	 */
	public Board getBoardById(int boardId) {
		return boardDao.get(boardId);
	}

	/**
	 * 根据topicId获取Topic对象
	 * @param topicId
	 * @return Topic
	 */
	public Topic getTopicByTopicId(int topicId) {
		return topicDao.get(topicId);
	}

	/**
	 * 获取回复帖子的对象
	 * @param postId
	 * @return 回复帖子的对象
	 */
	public Post getPostByPostId(int postId){
		return postDao.get(postId);
	}

	/**
	 * 将用户设为论坛版块的管理员
	 * @param boardId  论坛版块ID
	 * @param userName 设为论坛管理的用户名
	 */
	public void addBoardManager(int boardId,String userName){
		User user = userDao.getUserByUserName(userName);
		if(user == null){
			throw new RuntimeException("用户名为"+userName+"的用户不存在。");
		}else{
			Board board = boardDao.get(boardId);
			user.getManBoard().add(board);
			//user.getManBoards().add(board);
			userDao.update(user);
		}
	}

	/**
	 * 更改主题帖
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		topicDao.update(topic);
	}

	/**
	 * 更改回复帖子的内容
	 * @param post
	 */
	public void updatePost(Post post){
		postDao.update(post);
	}


}
