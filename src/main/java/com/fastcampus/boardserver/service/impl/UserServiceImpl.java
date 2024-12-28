package com.fastcampus.boardserver.service.impl;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptSHA256;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import java.time.LocalDateTime;
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
		userDTO.setUpdateTime(LocalDateTime.now());
		userDTO.setPassword(encryptSHA256(userDTO.getPassword()));

		int insertCount = userProfileMapper.insertUserProfile(userDTO);
		if (insertCount != 1) {
			log.error("insertUserProfile ERROR! {}", userDTO);
			throw new RuntimeException("insertUserProfile ERROR! 회원가입 메서드를 확인해주세요\n" + "Parameter : " + userDTO);
		}
	}

	@Override
	public UserDTO login(String id, String password) {
		String cryptPassword = encryptSHA256(password);
		return userProfileMapper.findByUserIdAndPassword(id, cryptPassword);
	}

	@Override
	public boolean isDuplicateId(String id) {
		return userProfileMapper.idCheck(id) == 1;
	}

	@Override
	public UserDTO getUserInfo(String userId) {
		return userProfileMapper.selectUserProfile(userId);
	}

	@Override
	public void updatePassword(String id, String beforePassword, String afterPassword) {
		String cryptoPassword = encryptSHA256(beforePassword);
		UserDTO userDTO = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);

		if (userDTO != null) {
			userDTO.setPassword(encryptSHA256(afterPassword));
			userProfileMapper.updatePassword(userDTO);
		} else {
			log.error("updatePassword ERROR! id: {}, beforePassword: {}, cryptoPassword: {}", id, beforePassword, cryptoPassword);
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
	}

	@Override
	public void deleteUser(String id, String password) {
		String cryptoPassword = encryptSHA256(password);
		UserDTO userDTO = userProfileMapper.findByUserIdAndPassword(id, cryptoPassword);

		if (userDTO != null) {
			userProfileMapper.deleteUserProfile(id);
		} else {
			log.error("deleteUser ERROR! id: {}, password: {}, cryptoPassword: {}", id, password, cryptoPassword);
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
	}
}
