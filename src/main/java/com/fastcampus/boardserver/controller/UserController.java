package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.UserDeleteId;
import com.fastcampus.boardserver.dto.request.UserLoginRequest;
import com.fastcampus.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.boardserver.dto.response.LoginResponse;
import com.fastcampus.boardserver.dto.response.UserInfoResponse;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

	private final UserServiceImpl userService;
	private static LoginResponse loginResponse;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping("sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public void signUp(@RequestBody UserDTO userDTO) {
		if (UserDTO.hasNullDataBeforeRegister(userDTO)) {
			throw new RuntimeException("회원가입 정보를 확인해주세요.");
		}
		userService.register(userDTO);
	}

	@PostMapping("sign-in")
	public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
		ResponseEntity<LoginResponse> responseEntity = null;
		String id = userLoginRequest.getUserId();
		String password = userLoginRequest.getPassword();
		UserDTO userDTO = userService.login(id, password);

		if (userDTO == null) {
			return HttpStatus.NOT_FOUND;
		} else if (userDTO != null) {
			loginResponse = LoginResponse.success(userDTO);
			if (userDTO.getStatus() == (UserDTO.Status.ADMIN)) {
				SessionUtil.setLoginAdminId(session, id);
			} else {
				SessionUtil.setLoginMemberId(session, id);
			}
			responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
		} else {
			throw new RuntimeException("Login ERROR! 유저 정보가 없거나 지원되지 않는 유저입니다.");
		}

		return HttpStatus.OK;
	}

	@GetMapping("my-info")
	public UserInfoResponse userInfo(HttpSession session) {
		String id = SessionUtil.getLoginMemberId(session);
		if (id == null) id = SessionUtil.getLoginAdminId(session);
		UserDTO userDTO = userService.getUserInfo(id);
		return new UserInfoResponse(userDTO);
	}

	@PutMapping("logout")
	public void logout(HttpSession session) {
		SessionUtil.clear(session);
	}

	@PatchMapping("password")
	@LoginCheck(userType = LoginCheck.UserType.USER)
	public ResponseEntity<LoginResponse> updatePassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest/*, HttpSession session*/) {
		ResponseEntity<LoginResponse> responseEntity = null;
		/*String id = SessionUtil.getLoginMemberId(session); // 로그인AOP추가로 불필요*/
		String beforePassword = userUpdatePasswordRequest.getBeforePassword();
		String afterPassword = userUpdatePasswordRequest.getAfterPassword();

		try {
			userService.updatePassword(accountId, beforePassword, afterPassword);
			ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
		} catch (IllegalArgumentException e) {
			log.error("updatePassword ERROR! {}", e.getMessage());
			responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	@DeleteMapping("delete")
	public ResponseEntity<LoginResponse> deleteUser(@RequestBody UserDeleteId userDeleteId, HttpSession session) {
		ResponseEntity<LoginResponse> responseEntity 		= null;
		String id = SessionUtil.getLoginMemberId(session);

		try {
			userService.deleteUser(id, userDeleteId.getPassword());
			responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
		} catch (RuntimeException e) {
			log.error("deleteUser ERROR! {}", e.getMessage());
			responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

}
