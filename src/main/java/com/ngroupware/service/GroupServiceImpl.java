package com.ngroupware.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ngroupware.dao.GroupDAO;
import com.ngroupware.vo.GroupPointVO;
import com.ngroupware.vo.GroupVO;
import com.ngroupware.vo.UserVO;


@Service
public class GroupServiceImpl implements GroupService {

	@Inject
	private GroupDAO dao;

	@Override
	public void GroupCreate(GroupVO groupVO) throws Exception {
		dao.GroupCreate(groupVO);
	}

	@Override
	//초대현황
	public List<GroupVO> groupCheck(UserVO userVO) throws Exception {
		return dao.groupCheck(userVO);
	}
	
	@Override
	//초대 yes
	public void groupYes(GroupVO groupVO) throws Exception {
		dao.groupYes(groupVO);
		
	}

	@Override
	//초대 no
	public void groupNo(GroupVO groupVO) throws Exception {
		dao.groupNo(groupVO);
		
	}
	
	@Override
	public List<GroupVO> GroupList(UserVO userVO) throws Exception {
		return dao.GroupList(userVO);
	}

	@Override
	//출석 생성시 그룹원들의 attend를 추가하기 위해 그룹원 이름 가져오기!
	public List<GroupVO> groupMemberList(GroupVO groupVO) throws Exception {
		return dao.groupMemberList(groupVO);
	}

	@Override
	public int GroupListCount(UserVO userVO) throws Exception {
		return dao.GroupListCount(userVO);
	}
	
	@Override
	public GroupVO findMaster(GroupVO groupVO) throws Exception {
		return dao.findMaster(groupVO);
	}

	@Override
	public void userPoint(GroupPointVO groupPointVO) throws Exception {
		dao.userPoint(groupPointVO);
	}

	@Override
	public List<GroupPointVO> findTopRankList(GroupVO groupVO) throws Exception {
		return dao.findTopRankList(groupVO);
	}

	@Override
	public GroupPointVO findMyRank(GroupVO groupVO) throws Exception {
		return dao.findMyRank(groupVO);
	}

	@Override
	public void socketConnection(GroupVO groupVO) throws Exception {
		dao.socketConnection(groupVO);
	}

	@Override
	public List<GroupVO> socketConnectionList(GroupVO groupVO) throws Exception {
		return dao.socketConnectionList(groupVO);
	}
	
	@Override
	public GroupVO groupRead(GroupVO groupVO) throws Exception {
		return dao.groupRead(groupVO);
	}
	
	@Override
	public void userPlus(GroupVO groupVO) throws Exception {
		dao.userPlus(groupVO);
	}

	@Override
	public void userPlusMailAuth(GroupVO groupVO) throws Exception {
		dao.userPlusMailAuth(groupVO);
	}

	@Override
	public void groupLock(GroupVO groupVO) throws Exception {
		dao.groupLock(groupVO);
	}
}
