package com.ngroupware.controller;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ngroupware.service.AttendService;
import com.ngroupware.service.GroupService;
import com.ngroupware.vo.AttendListVO;
import com.ngroupware.vo.AttendVO;
import com.ngroupware.vo.GroupVO;

import net.sf.json.JSON;

@Controller
public class AttendController {
	
	
	@Inject
	AttendService attendService;
	@Inject
	GroupService groupService;
	
	private static final Logger logger = LoggerFactory.getLogger(AttendController.class);
	
	//attend 홈페이지로 이동할 때 사용
	@RequestMapping(value = "/attend", method = RequestMethod.GET)
	public String noticeget(HttpServletRequest request, Model model) throws Exception {
		logger.info("attend_get");

		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		model.addAttribute("g_flag", groupVO.getG_flag());
		

		AttendVO attendLast = attendService.attendLast(groupVO);
		return "attend";
	}
	/*
	//attend화면 들어왔을 때 현재 시간과의 비교를 통해 attendUpdate
	@RequestMapping(value = "/attendUpdate", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public void attendUpdatget(HttpServletRequest request, Model model) throws Exception {
		logger.info("attendUpdate_get");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		attendService.attendUpdate(groupVO);
	}
	*/
	
	//attendList를 가져오는 부분
	@RequestMapping(value = "/attendList", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public Object attendget(HttpServletRequest request, Model model) throws Exception {
		logger.info("attendList_get");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");

		//list를 화면에 나타낼 때 시간비교 후 출석현황 update
		attendService.attendUpdate(groupVO);
		List<AttendVO> attendList = attendService.attendList(groupVO);
		return attendList;
	}
	
	//새로운 출석 생성
	@RequestMapping(value = "/attendMake", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public Object attendMaketpost(HttpServletRequest request, Model model) throws Exception {
           
		logger.info("attendMake_post");

		String limitTime = request.getParameter("limitTime");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		
		AttendVO attendVO = new AttendVO();
		attendVO.setG_no(groupVO.getG_no());
		attendVO.setA_deadLine(limitTime);
		
		//그룹멤버 목록을 불러오자
		List<GroupVO> memberList = groupService.groupMemberList(groupVO);
		for(int i=0; i<memberList.size(); i++) {
			attendVO.setU_id(memberList.get(i).getU_id());		
			//출석 추가
			attendService.attendMake(attendVO);
		}
		
		AttendVO attendLast = attendService.attendLast(groupVO);
		return attendLast;
		
  	}
	
	//제한시간 지났을 때 결석처리하는 부분
	@RequestMapping(value = "/attendClose", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public Object attendClosepost(HttpServletRequest request) throws Exception {
           
		logger.info("attendClose_post");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		String a_date = request.getParameter("a_date");
		String a_deadLine= request.getParameter("a_deadLine");
		
		AttendVO attendVO = new AttendVO();
		attendVO.setG_no(groupVO.getG_no());
		attendVO.setA_date(a_date);
		attendVO.setA_deadLine(a_deadLine);
		
		//너희 출석안해? 너네 다 결석.
		List<GroupVO> memberList = groupService.groupMemberList(groupVO);
		for(int i=0; i<memberList.size(); i++) {
			
			attendVO.setU_id(memberList.get(i).getU_id());		
//			출석 추가
			attendService.attendClose(attendVO);
		}
		
		String msg = "출석이 마감되었습니다.";	
		return msg;
		
  	}
	
	@RequestMapping(value = "/attendClick", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public Object attendCheckpost(HttpServletRequest request) throws Exception {
        String msg = ""; 
		logger.info("attendClick_post");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		
		AttendVO attendVO = new AttendVO();
		String deadLine= request.getParameter("deadLine");
		
		attendVO.setG_no(groupVO.getG_no());
		attendVO.setU_id(groupVO.getU_id());
		attendVO.setA_deadLine(deadLine);
		
		attendService.attendClick(attendVO);
		int a_flag = attendService.attendSelect(attendVO).getA_flag();
		
		if(a_flag == 1) {
			msg = "출석처리가 완료되었습니다.";
		}
		else {
			msg = "출석처리가 되지 않았습니다.";
		}
		
		return msg;
		
  	}
	
	//출석관리 화면으로 이동(수정중)
	@RequestMapping(value = "/attendDetail", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public Object attendDetailget(HttpServletRequest request) throws Exception {
           
		logger.info("attendDetail_get");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		String a_date = request.getParameter("a_date");
		
		AttendVO attendVO = new AttendVO();
		attendVO.setG_no(groupVO.getG_no());
		attendVO.setA_date(a_date);
		
		List<AttendListVO> attendDetail = attendService.attendDetail(attendVO);
		
		return attendDetail;
		
  	}
	
	@RequestMapping(value = "/attendManage", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public Object attendManagePOST(HttpServletRequest request) throws Exception {
           
		logger.info("attendManage_POST");
		
		//세션으로 u_id, g_no 받아와서 사용
		HttpSession session = request.getSession();
		GroupVO groupVO = (GroupVO)session.getAttribute("group");
		String a_date = request.getParameter("a_date");
		String [] modifiedArr = request.getParameterValues("modifiedArr");
		
		AttendVO attendVO = new AttendVO();
		attendVO.setG_no(groupVO.getG_no());
		attendVO.setA_date(a_date);

		for(int i=0; i<modifiedArr.length; i++) {
				
			attendVO.setU_id(modifiedArr[i++]);
			attendVO.setA_flag(Integer.parseInt(modifiedArr[i]));
			
			attendService.attendManage(attendVO);
		}
		
		String msg = "수정이 완료되었습니다.";
		return msg;
		
  	}
	
	
	
	
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public Object testpost(HttpServletRequest request) throws Exception {
           
		logger.info("/test_post");

		String test = request.getParameter("test");
		logger.info(test);

		String msg = "컨트롤러에서 뷰로";
		return msg;
	}
}