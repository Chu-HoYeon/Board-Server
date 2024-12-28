package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper {

	int insertUserProfile(UserDTO userDTO);

}
