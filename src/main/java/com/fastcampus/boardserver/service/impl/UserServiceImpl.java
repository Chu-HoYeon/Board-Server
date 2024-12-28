package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	private final UserProfileMapper userProfileMapper;

	public UserServiceImpl(UserProfileMapper userProfileMapper) {
		this.userProfileMapper = userProfileMapper;
	}

	@Override
	public void register(UserDTO userDTO) {
		boolean duplicateId = isDuplicateId(userDTO.getUserId());
		if (duplicateId) {
			throw new DuplicateIdException("중복된 아이디입니다.");
		}
		userDTO.setCreateTime(LocalDateTime.now());
		int result = userProfileMapper.insertUserProfile(userDTO);
	}

	@Override
	public UserDTO login(String id, String password) {
		return null;
	}

	@Override
	public boolean isDuplicateId(String id) {
		return false;
	}

	@Override
	public UserDTO getUserInfo(String userId) {
		return null;
	}

	@Override
	public void updatePassword(String id, String beforePassword, String afterPassword) {

	}

	@Override
	public void deleteUser(String id) {

	}
}
