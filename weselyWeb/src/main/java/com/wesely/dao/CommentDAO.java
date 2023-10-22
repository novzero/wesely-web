package com.wesely.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wesely.vo.CommentVO;
/*
 * <!-- 1. ref에 대한 댓글의 개수 구하기 : 목록보기에 댓글 개수 출력 -->
	<select id="selectCountByRef" parameterType="int" resultType="int">
		select count(*) from comm where ref=#{ref} 
	</select>
	<!-- 2. ref에 대한 모든 댓글 얻기 -->
	<select id="selectListByRef" parameterType="int" resultType="CommentVO">
		select * from comm where ref=#{ref} order by id desc
	</select>
	<!-- 3. 댓글 저장 -->
	<insert id="insert" parameterType="CommentVO">
		insert into 
			comm
		values
			(comm_id_seq.nextval, #{ref},#{password},#{content},sysdate,0)
	</insert>
	<!-- 4. 댓글 수정 -->
	<update id="update" parameterType="BCommentVO">
		update 
			comm
		set
			content=#{content}, regdate=sysdate
		where
			id=#{id}
	</update>
	<!-- 5. 댓글 1개 삭제 -->
	<delete id="delete" parameterType="int">
		delete from comm where id=#{id}
	</delete>	
	<!-- 6. 지정 ref대한 모든 댓글 삭제하기 -->
	<delete id="deleteByRef" parameterType="int">
		delete from comm where ref=#{ref}
	</delete>	
	<!-- 7. 1개 얻기  -->
	<select id="selectById" parameterType="int" resultType="CommentVO">
		select * from comm where id=#{id}
	</select>*/
@Mapper
public interface CommentDAO {
//	<!-- 1. ref에 대한 댓글의 개수 구하기 : 목록보기에 댓글 개수 출력 -->
	int selectCountByRef(int ref);
//	<!-- 2. ref에 대한 모든 댓글 얻기 -->
	List<CommentVO> selectListByRef(int ref);
//	<!-- 3. 댓글 저장 -->
	void insert(CommentVO vo);
//	<!-- 4. 댓글 수정 -->
	void update(CommentVO vo);
//	<!-- 5. 댓글 1개 삭제 -->
	void delete(int id);
//	<!-- 6. 지정 ref대한 모든 댓글 삭제하기 -->
	void deleteByRef(int ref);
//	<!-- 7. 1개 얻기  -->
	CommentVO selectById(int id);
// 8. 수정하기 : 회원정보 수정
	void updateName(HashMap<String, String> map);
}
