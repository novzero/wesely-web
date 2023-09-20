package com.wesely.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.CommentDAO;
import com.wesely.dao.CommunityDAO;
import com.wesely.dao.CommunityImgDAO;
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.Paging;

import lombok.extern.slf4j.Slf4j;

@Service("communityService")
@Slf4j
public class CommunityServiceImpl implements CommunityService {

	@Autowired
	private CommunityDAO communityDAO;

	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private CommunityImgDAO communityImgDAO;

	// 1개 얻어 조회수 증가
	@Override
	public CommunityVO selectById(int id, int mode) {
		log.info("selectById({},{}) 호출!!!", id, mode);
		CommunityVO vo = null;
		// ------------------------------------------------------------------------------------
		vo = communityDAO.selectById(id);
		if (vo != null) {
			if (mode == 1) {
				communityDAO.updateReadCount(id); // 조회수 증가 : DB의 조회수 증가
			}
			// 이미 가져온 VO는 조회수가 증가전이다.
			// 다시 DB에서 가져오면 속도가 떨어진다.
			// 그냥 조회수를 1증가 시키는것이 더 효율적이다.
			vo.setReadCount(vo.getReadCount() + 1);
		}
		// ------------------------------------------------------------------------------------
		log.info("selectById({},{}) 리턴 : {}", id, mode, vo);
		return vo;
	}

	// 저장
	@Override
	public boolean insert(CommunityVO communityVO) {
		log.info("insert 호출 :{}", communityVO);
		// 이름이 있고 같다면
		boolean result = false;
		if (communityVO != null) {
			if(communityVO.getNickname()!= null && communityVO.getNickname().trim().length()>0) {				
			
			}
			// 글 저장
			communityDAO.insert(communityVO);
			// 이미지 저장
			for(CommunityImgVO vo : communityVO.getImgList()) {
				communityImgDAO.insert(vo);
			}
		}
		// -----------------------------------------------------------------------------
		log.info("insert 리턴 : {}", result);
		return result;
	}

	// 커뮤니티 글 수정
	@Override
	public boolean update(CommunityVO communityVO, String delList,String filePath) {
		log.info("update({},{},{}) 호출:", communityVO,delList,filePath);
		boolean result = false;
		// 글 존재하고 비번같으면 수정
		if (communityVO != null) {
			CommunityVO dbVO = communityDAO.selectById(communityVO.getId());
			if (dbVO != null ) {
				communityDAO.update(communityVO);
				result = true;
			}
			for(CommunityImgVO vo: communityVO.getImgList()) {
				communityImgDAO.insert2(vo);
			}
			// 삭제 파일을 삭제한다.
			if(delList!=null && delList.length()>0) {
				String[] delFile = delList.trim().split(" ");
				if(delFile!=null && delFile.length>0) {
					for(String s : delFile) {
						// 파일 삭제
						// 서버 저장된 파일삭제
						CommunityImgVO communityImgVO = communityImgDAO.selectById(Integer.parseInt(s));
						String fileName = communityImgVO.getUuid()+"_"+communityImgVO.getFileName();
						File file = new File(filePath,fileName);
						if(file.exists()){ // 파일이 존재하면
							file.delete();
						}
						communityImgDAO.deleteById(Integer.parseInt(s));
					}
					result =true;
				}
			}
		}
		log.info("update 리턴: {}", result);
		return result;
	}

	// 커뮤니티 글 삭제
	@Override
	public boolean delete(CommunityVO communityVO) {
		log.info("delete 호출 : {}", communityVO);
		boolean result = false;
		// 비번 같으면 커뮤 글삭제
		if (communityVO != null) {
			CommunityVO dbVO = communityDAO.selectById(communityVO.getId());
			if (dbVO != null) {
				communityDAO.delete(communityVO.getId());
				// 커뮤 댓글도 삭제
				commentDAO.deleteByRef(communityVO.getId());
				result = true;
			}
		}
		log.info("delet 리턴 : {}", result);
		return result;
	}
	// 내용보기
	@Override
	public CommunityVO view(int id) {
		log.info("view 호출 : {}", id);
		CommunityVO communityVO = null;
		//-----------------------------------------------------------------------------
		// 1. 해당 번호의 글을 읽어온다.
		communityVO = communityDAO.selectById(id);
		// 2. 해당 글이 존재한다면 댓글들을 모두 가져온다.
		if(communityDAO!=null) {
			List<CommentVO> commentList = commentDAO.selectListByRef(id);
			// 3. 댓글을 VO에 넣어준다.
			communityVO.setCommentList(commentList);
		}
		//-----------------------------------------------------------------------------
		log.info("view 리턴 : {}", communityVO);
		return communityVO;
	}
	// 댓글저장
	@Override
	public boolean commentInsert(CommentVO commentVO) {
		log.info("commentInsert 호출 : {}", commentVO);
		boolean result =false;
		
		if(commentVO!=null) {
			commentDAO.insert(commentVO);
			result = true;
		}
		log.info("commentInsert 리턴 : {}", result);
		return result;
	}
	// 댓글수정
	@Override
	public boolean commentUpdate(CommentVO commentVO) {
		log.info("commentUpdate 호출 : {}", commentVO);
		boolean result = false;
		
		if(commentVO!=null) {
			CommentVO dbVO = commentDAO.selectById(commentVO.getId());
			if(dbVO!=null ) {
				commentDAO.update(dbVO);
				result = true;
			}
		}
		log.info("commentUpdate 리턴 : {}", commentVO);
		return result;
	}
	// 댓글 삭제
	@Override
	public boolean commentDelete(CommentVO commentVO) {
		log.info("commentDelete 호출 : {}", commentVO);
		boolean result = false;
		// 글이 없으면
		if(commentVO!=null) {
			// id 1개 가져오는걸 dbVo로 넣어준다.
			CommentVO dbVO = commentDAO.selectById(commentVO.getId());
			if(dbVO!= null ) {
				commentDAO.delete(commentVO.getId());
				result = true;
			}
		}
		log.info("commentDelete 리턴 : {}", commentVO);
		return result;
	}
	
	// 목록보여주기
	@Override
	public Paging<CommunityVO> selectList(int currentPage, int sizeOfPage, int sizeOfBlock) {
		int toatalCount = communityDAO.selectCount();
		Paging<CommunityVO> paging = new Paging<>(toatalCount, currentPage, sizeOfPage, sizeOfBlock);
		HashMap<String, Integer> map = new HashMap<>();
		map.put("startNo", paging.getStartNo());
		map.put("endNo", paging.getEndNo());
		List<CommunityVO> list = communityDAO.selectList(map);
		if(list!=null) {
			for(CommunityVO vo : list) {
				List<CommunityImgVO> list2 = communityImgDAO.selectByRef(vo.getId());
				vo.setImgList(list2);
			}
		}
		paging.setList(list);
		System.out.println(paging);
		return paging;
	}


}
