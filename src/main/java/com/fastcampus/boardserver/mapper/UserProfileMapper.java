package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserProfileMapper {

	/* 유저 생성 */
	int insertUserProfile(UserDTO userDTO);

	/* 유저 조회 */
	UserDTO selectUserProfile(@Param("userId") String userId);

	/* 유저 삭제 */
	void deleteUserProfile(@Param("userId") String userId);

	/* 로그인 */
	UserDTO findByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

	/* 유저 아이디 중복확인 */
	int idCheck(@Param("userId") String userId);

	/* 패스워드 수정 */
	void updatePassword(UserDTO userDTO);

}
