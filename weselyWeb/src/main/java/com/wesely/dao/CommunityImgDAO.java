package com.wesely.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.CommunityImgVO;
import com.wesely.vo.CommunityVO;
/*
 * <!-- 1. 지정 ref의 모든 파일목록 얻기 -->
	<select id="selectByRef" parameterType="int" resultType="CommunityImgVO">
		select * from CommunityImg where ref=#{ref}
	</select>
	<!-- 2. 저장 -->
	<insert id="insert" parameterType="CommunityImgVO">
		insert into 
			CommunityImg
		values
			(CommunityImg_id_seq.nextval, Community_id_seq.currval,#{uuid},#{fileName},#{contentType})
	</insert>
	<insert id="insert2" parameterType="CommunityImgVO">
		insert into 
			CommunityImg
		values
			(CommunityImg_id_seq.nextval, #{ref},#{uuid},#{fileName},#{contentType})
	</insert>
	<!-- 3. 지정 ref의 모든 파일 지우기 -->
	<delete id="deleteByRef" parameterType="int">
		delete from CommunityImg where ref=#{ref}
	</delete>
	<!-- 4. 지정 id의 파일 지우기 -->
	<delete id="deleteById" parameterType="int">
		delete from CommunityImg where id=#{id}
	</delete>
	<!-- 5. 1개 얻기 -->
	<select id="selectById" parameterType="int" resultType="CommunityImgVO">
		select * from CommunityImg where id=#{id}
	</select>
 * */

@Mapper
public interface CommunityImgDAO {
	// 전체 읽기
	List<CommunityImgVO> selectByRef(int ref);
	// 저장
	void insert(CommunityImgVO communityImgVO);
	// 수정
	void insert2(CommunityImgVO communityImgVO);
	// 글의 이미지 전체 삭제
	void deleteByRef(int ref);
	// 지정 id 파일 삭제
	void deleteById(int id);
	// 1개 얻기
	CommunityImgVO selectById(int id);
	
}
