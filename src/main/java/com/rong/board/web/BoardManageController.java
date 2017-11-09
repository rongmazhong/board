package com.rong.board.web;

import com.rong.board.domain.Board;
import com.rong.board.domain.CommonConstant;
import com.rong.board.domain.Page;
import com.rong.board.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author:rong
 * Description:
 * Data: Create in 下午 4:37 17.11.9
 * Package: com.rong.board.web
 */
@Controller
public class BoardManageController extends BaseController {
	@Autowired
	private ForumService forumService;

	/**
	 * 获取论坛版块下的主题帖
	 * @param boardId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "/board/listBoardTopics-{boardId}",method = RequestMethod.GET)
	public ModelAndView listBoardTopics(@PathVariable Integer boardId,
	                                    @RequestParam (value = "pageNo",required = false) Integer pageNo){
		ModelAndView modelAndView = new ModelAndView();
		Board board = forumService.getBoardById(boardId);
		pageNo = pageNo ==null?1:pageNo;
		Page pageTopic = forumService.getPagedTopics(boardId,pageNo, CommonConstant.PAGE_SIZE);
		modelAndView.addObject("board",board);
		modelAndView.addObject("PageTopic",pageTopic);
		modelAndView.setViewName("/listBoardTopics");
		return modelAndView;
	}


}
