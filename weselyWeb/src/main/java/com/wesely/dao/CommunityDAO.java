package com.wesely.dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.wesely.vo.CommunityVO;
/*<!-- 1. 전체 개수 얻기  -->
	<select id="selectCount" resultType="int">
		select count(*) from Community
	</select>
   <!-- 2. 1개 얻기  -->
   	<select id="selectById"  parameterType="int" resultType="CommunityVO">
   		select * from board2 where id=#{id}
   	</select>
   <!-- 3. 1페이지 얻기  -->
   <select id="selectList" parameterType="hashmap" resultType="CommunityVO">
	 select 
	 	Q.*
	 from	
	   	(select
	   		rownum rnum, R.*
	   		from	
	   			(select * from board2 order by id desc) R 
	   		where
	   			<![CDATA[
	   				rownum <= #{endNo}
	   			]]>
	   		) Q
	   where
	   			<![CDATA[
	   				rownum >= #{startNo}
	   			]]>
   </select>
   <!-- 4. 저장하기  -->
   <insert id="insert" parameterType="CommunityVO" >
   		insert into board2 values (board2_id_seq.nextval, #{name},#{password,},#{subject},#{content},sysdate,0,#{ip})
   </insert>
   <!-- 5. 수정하기  -->
   <update id="update" parameterType="CommunityVO">
   		update board2 set subject=#{subject}, content=#{content}, regdate=sysdate, ip=#{ip} where id=#{id}
   </update>
   <!-- 6. 삭제하기  -->
   <delete id="delete"  parameterType="CommunityVO">
   		delete  from board2 where id=#{id}
   </delete>
   <!-- 7. 조회수 증가하기  -->
   <update id="updateReadCount" parameterType="int">
   		update board2 set readCount = readCount+1 where id=#{id}
   </update>
 * 
 * */
import com.wesely.vo.MemberVO;

@Mapper
public interface CommunityDAO {
//	   <!-- 1. 전체 개수 얻기  -->
		int selectCount();
//	   <!-- 2. 1개 얻기  -->
		CommunityVO selectById(int id);
//		<!-- 아이디로 찾기 -->
		CommunityVO selectByUserid(String userid);
//	   <!-- 3. 1페이지 얻기  -->
		List<CommunityVO> selectList(HashMap<String, Integer>map);
//	   <!-- 4. 저장하기  -->
		void insert(CommunityVO communityVO);
//	   <!-- 5. 수정하기  -->
		void update(CommunityVO communityVO);
//	   <!-- 6. 삭제하기  -->
		void delete(int id);
//	   <!-- 7. 조회수 증가하기  -->
		void updateReadCount(int id);
		// <!-- 수정하기 : 회원정보 수정 -->
		void updateNickname(HashMap<String, String> map);
}
