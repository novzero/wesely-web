package com.wesely.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesely.dao.CommentDAO;
import com.wesely.dao.CommunityDAO;
import com.wesely.dao.CommunityImgDAO;
import com.wesely.dao.MemberDAO;
import com.wesely.dao.cgoodDAO;
import com.wesely.dao.goodDAO;
import com.wesely.vo.CGoodVO;
//github.com/novzero/wesely-web.git
import com.wesely.vo.CommentVO;
import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
import com.wesely.vo.GoodVO;
//github.com/novzero/wesely-web.git
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
   
   @Autowired
   private MemberDAO memberDAO;
   
   @Autowired
   private goodDAO goodDAO;
   
   @Autowired
   private cgoodDAO cgoodDAO;
   
   // 1개 얻어 조회수 증가
   @Override
   public CommunityVO selectById(int id, int mode) {
      log.info("selectById({},{}) 호출!!!", id, mode);
      CommunityVO vo = null;
      // ------------------------------------------------------------------------------------
      // 아이디 불러오기를 vo에 담아준다.
      vo = communityDAO.selectById(id);
      // vo의 내용이 있으면
      if (vo != null) {
         // (mode ==1 이면 조회수 증가 0이면 안증가)
         if (mode == 1) {
            communityDAO.updateReadCount(id); // 조회수 증가 : DB의 조회수 증가
            // 3. 리뷰를 VO에 넣어준다.
            
         }
         // 이미 가져온 VO는 조회수가 증가전이다.
         // 다시 DB에서 가져오면 속도가 떨어진다.
         // 그냥 조회수를 1증가 시키는것이 더 효율적이다.
         vo.setReadCount(vo.getReadCount() + 1);
      }
      // ------------------------------------------------------------------------------------
      // CommunityVO에 내용과 이미지를 입력함
      List<CommentVO> list = commentDAO.selectListByRef(vo.getId());
      vo.setCommentList(list);
      vo.setCommentCount(list.size());
      vo.setImgList(communityImgDAO.selectByRef(id));
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
         // 닉네임이 있다면 (글자수 공백포함 1개라도 있고 널이 아니면 )
         if (communityVO.getNickname() != null && communityVO.getNickname().trim().length() > 0) {

         }
         // 글 저장
         communityDAO.insert(communityVO);
         // 이미지 저장
         for (CommunityImgVO vo : communityVO.getImgList()) {
            communityImgDAO.insert(vo);
         }
      }
      // -----------------------------------------------------------------------------
      log.info("insert 리턴 : {}", result);
      return result;
   }

   // 커뮤니티 글 수정
   @Override
   public boolean update(CommunityVO communityVO, String delList, String filePath) {
      log.info("update({},{},{}) 호출:", communityVO, delList, filePath);
      boolean result = false;
      // 글 존재하면 
      if (communityVO != null) {
         // 수정
         communityDAO.update(communityVO);
         // 파일저장 커뮤니티내용에있는 이미지리스트를 vo에 넣어서 
         for (CommunityImgVO vo : communityVO.getImgList()) {
            // 수정
            communityImgDAO.insert2(vo);
         }
         // 삭제 파일을 삭제한다.
         if (delList != null && delList.length() > 0) {
            String[] delFile = delList.trim().split(" ");
            if (delFile != null && delFile.length > 0) {
               for (String s : delFile) {
                  // 파일 삭제
                  // 서버 저장된 파일삭제
                  CommunityImgVO communityImgVO = communityImgDAO.selectById(Integer.parseInt(s));
                  // 파일이름은 vo에 있는 uuid(랜덤명 wakwuefh223) + _ +파일명(pepe)
                  String fileName = communityImgVO.getUuid() + "_" + communityImgVO.getFileName();
                  // filePath (위치할 경로) fileName(파일이름) 을 file 에 담는다
                  File file = new File(filePath, fileName);
                  if (file.exists()) { // 파일이 존재하면
                     file.delete();
                  }
                  // db의 정보도 삭제
                  communityImgDAO.deleteById(Integer.parseInt(s));
               }
               result = true;
            }
         }
      }
      log.info("update 리턴: {}", result);
      return result;

   }

   // 커뮤니티 글 삭제
   @Override
   public boolean delete(CommunityVO communityVO, String filePath) {
      log.info("delete 호출 :{}, {}", communityVO, filePath);
      boolean result = false;
      // 글있으면 커뮤 글삭제
      if (communityVO != null) {
         // 커뮤니티의 ID에 해당하는 이미지 정보를 가져와서 fileList에 담는다.
         List<CommunityImgVO> fileList = communityImgDAO.selectByRef(communityVO.getId());
         // 파일리스트가 존재한다면
         if (fileList != null && fileList.size() > 0) {
            // 파일리스트를 vo에 담아서 반복해줌
            for (CommunityImgVO vo : fileList) {
               // 파일이름은 vo에 있는 uuid(랜덤명 wakwuefh223) + _ +파일명(pepe)
               String fileName = vo.getUuid() + "_" + vo.getFileName();
               // filePath (위치할 경로) fileName(파일이름) 을 file 에 담는다
               File file = new File(filePath + fileName);
               // getAbsolutePath 파일 절대경로
               log.info("파일 경로" + file.getAbsolutePath());
               // 파일이 존재하면
               if (file.exists()) {
                  log.info("파일 있음");
                  // 파일을 삭제
                  file.delete();
               } else {
                  log.info("파일 없음");
               }
               // 데이터 베이스에서 지움
               communityImgDAO.deleteById(vo.getId());
            }
         }
         // 글도 삭제
         communityDAO.delete(communityVO.getId());
         result = true;
      }
      return result;
   }

   // 내용보기
   @Override
   public CommunityVO view(int id) {
      log.info("view 호출 : {}", id);
      CommunityVO communityVO = null;
      // -----------------------------------------------------------------------------
      // 1. 해당 번호의 글을 읽어온다.
      communityVO = communityDAO.selectById(id);
      // 2. 해당 글이 존재한다면 댓글들을 모두 가져온다.
      if (communityDAO != null) {
         List<CommentVO> commentList = commentDAO.selectListByRef(id);
         // 3. 댓글을 VO에 넣어준다.
         communityVO.setCommentList(commentList);
         // 댓글 개수 넣기
         communityVO.setCommentCount(commentDAO.selectCountByRef(communityVO.getId()));
         // 좋아요 개수 넣기
         
         
      }
      // -----------------------------------------------------------------------------
      log.info("view 리턴 : {}", communityVO);
      return communityVO;
   }

   // 댓글저장
   @Override
   public boolean commentInsert(CommentVO commentVO) {
      log.info("commentInsert 호출 : {}", commentVO);
      boolean result = false;
      // 댓글내용이있으면
      if (commentVO != null) {
         // 저장
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
      // 글내용 있으면
      if (commentVO != null) {
         CommentVO dbVO = commentDAO.selectById(commentVO.getId());
         // 댓글 내용있으면
         if (dbVO != null) {
            commentDAO.update(commentVO);
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
      // 글이 있으면
      if (commentVO != null) {
         // id 1개 가져오는걸 dbVo로 넣어준다.
         CommentVO dbVO = commentDAO.selectById(commentVO.getId());
         if (dbVO != null) {
            // 댓글삭제해줌
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
      // 페이지 전체개수 구하기
      int toatalCount = communityDAO.selectCount();
      // 페이지 계산하기
      Paging<CommunityVO> paging = new Paging<>(toatalCount, currentPage, sizeOfPage, sizeOfBlock);
      // 한페이지 분량 글 목록 가져오기
      HashMap<String, Integer> map = new HashMap<>();
      // 첫번쨰 번호랑 마지막번호를 맵에다가 넣어줌
      map.put("startNo", paging.getStartNo());
      map.put("endNo", paging.getEndNo());
      List<CommunityVO> list = communityDAO.selectList(map);
      // 리스트의 각각에 대한 댓글 개수를 가져온다.
      if (list != null) {
         
         for (CommunityVO vo : list) {
            // 반복중인 vo ID에 해당하는 이미지를 가져온다.
            List<CommunityImgVO> list2 = communityImgDAO.selectByRef(vo.getId());
            // 가져온걸 이미지리스트에 집어넣는다.
            vo.setImgList(list2);
         }
      }
      // 글목록을 페이징 객체에 넣어준다.
      paging.setList(list);
      System.out.println(paging);
      return paging;
   }
// 좋아요 등록
	@Override
	public boolean goodInsert(GoodVO goodVO) {
		log.info("goodInsert 호출 : {}", goodVO);
		boolean result = false;
		if(goodVO.getNickname()!= null && goodVO.getNickname().trim().length() > 0) {
			goodDAO.insertGood(goodVO);
			
			result = true;
		}
		log.info("goodInsert 리턴 : {}", goodVO);
		return result;
	}
	// 좋아요 삭제
	@Override
	public boolean goodDelete(GoodVO goodVO) {
	    log.info("goodDelete 호출 : {}", goodVO);
	    boolean result = false;
	    if(goodVO != null) {
	        goodDAO.deleteGood(goodVO);  // 여기도 GoodVO 객체 전체 전달
	        result = true;
	    }
	    log.info("goodDelete 리턴 : {}", result);
	    return result;
	}
	// 좋아요 체크확인
	@Override
	public int goodCheck(Map<String, Object> map) {
		
		return goodDAO.goodCheck(map);
	}
	// 좋아요 개수 가져오기
	@Override
	public int countGood(int ref) {
		return goodDAO.CountGood(ref);
	}

	// 댓글 좋아요 등록
	@Override
	public boolean cgoodInsert(CGoodVO cGoodVO) {
		log.info("cgoodInsert 호출 : {}", cGoodVO);
		boolean result = false;
		if(cGoodVO.getNickname()!= null && cGoodVO.getNickname().trim().length() > 0) {
			cgoodDAO.CommInsertGood(cGoodVO);
			
			result = true;
		}
		log.info("cgoodInsert 리턴 : {}", cGoodVO);
		return result;
	}
	// 댓글 좋아요 삭제
	@Override
	public boolean cgoodDelete(CGoodVO cGoodVO) {
		 log.info("cgoodDelete 호출 : {}", cGoodVO);
		    boolean result = false;
		    if(cGoodVO != null) {
		        cgoodDAO.CommDeleteGood(cGoodVO);  // 여기도 GoodVO 객체 전체 전달
		        result = true;
		    }
		    log.info("cgoodDelete 리턴 : {}", result);
		    return result;
	}
	// 댓글 좋아요 체크확인
	@Override
	public int cgoodCheck(Map<String, Object> map) {
		return cgoodDAO.CommGoodCheck(map);
	}
	// 댓글 좋아요 개수 가져오기
	@Override
	public int ccountGood(int ref) {
		return cgoodDAO.CommCountGood(ref);
	}
}