package com.ngroupware.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngroupware.service.HomeworkService;
import com.ngroupware.service.NoticeService;
import com.ngroupware.vo.GroupVO;
import com.ngroupware.vo.HomeworkVO;
import com.ngroupware.vo.NoticeVO;

@Controller
public class MainController {

	@Inject
	NoticeService noticeService;
	@Inject
	HomeworkService homeworkService;

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainget(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		logger.info("main_get");

		/*
		 * //로그인 처리 부분의 세션으로 대체 할 예정 GroupVO groupVO = new GroupVO();
		 * groupVO.setG_no(1); groupVO.setG_name("테스트그룹1"); groupVO.setU_id("ttest");
		 * groupVO.setG_flag(0);
		 */

		HttpSession session = request.getSession();
		

		GroupVO groupVO = (GroupVO) session.getAttribute("group");

		/*
		 * // 그룹 선택안하면 로그인 창으로 if (groupVO == null) { return "redirect:checkmate"; }
		 */
		
		
		
		
		
		
		
		
		

		/*
		 * int noticeListCount = noticeService.noticeListCount(groupVO);
		 * 
		 * if (noticeListCount == 0) { model.addAttribute("noticeIsNull", 1); } else {
		 * List<NoticeVO> noticeList = noticeService.noticeListMini(groupVO);
		 * model.addAttribute("noticeIsNull", 0); model.addAttribute("noticeList",
		 * noticeList); logger.info("noticeisNull : 0"); }
		 * 
		 * int homeworkListCount = homeworkService.homeworkListCount(groupVO);
		 * 
		 * logger.info(Integer.toString(homeworkListCount));
		 * 
		 * if (homeworkListCount == 0) { model.addAttribute("homeworkIsNull", 1); } else
		 * { List<HomeworkVO> homeworkList = homeworkService.homeworkListMini(groupVO);
		 * model.addAttribute("homeworkIsNull", 0); model.addAttribute("homeworkList",
		 * homeworkList); }
		 */
		
		
		
		
		
		
		
		model.addAttribute("noticeIsNull", 1);
		
		model.addAttribute("homeworkIsNull", 1);

		
		
		
		
		
		
		
		
		
		
		return "main";
	}

}
