package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.aop.LoginCheck.UserType;
import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.impl.PostServiceImpl;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

	private final UserServiceImpl userService;
	private final PostServiceImpl postService;

	public PostController(UserServiceImpl userService, PostServiceImpl postService) {
		this.userService = userService;
		this.postService = postService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(userType = UserType.USER)
	public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId,@RequestBody PostDTO postDTO) {
		postService.register(accountId, postDTO);
		CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);
		return ResponseEntity.ok(commonResponse);
	}

	@GetMapping("my-posts")
	@LoginCheck(userType = UserType.USER)
	public ResponseEntity<CommonResponse<List<PostDTO>>> myPosts(String accountId) {
		UserDTO userDTO = userService.getUserInfo(accountId);
		List<PostDTO> postDTOList = postService.getMyPosts(userDTO.getId());
		CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPosts", postDTOList);
		return ResponseEntity.ok(commonResponse);
	}

	@PatchMapping("{postId}")
	@LoginCheck(userType = UserType.USER)
	public ResponseEntity<CommonResponse<PostResponse>> updatePosts(String accountId,
			@PathVariable("postId") int postId,
			@RequestBody PostRequest postRequest) {

		UserDTO userDTO = userService.getUserInfo(accountId);
		PostDTO postDTO = PostDTO.builder()
			.id(postId)
			.name(postRequest.getName())
			.contents(postRequest.getContents())
			.categoryId(postRequest.getCategoryId())
			.userId(userDTO.getId())
			.fileId(postRequest.getFileId())
			.updateTime(LocalDateTime.now())
			.build();
		postService.updatePosts(postDTO);
		CommonResponse<PostResponse> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", new PostResponse(List.of(postDTO)));
		return ResponseEntity.ok(commonResponse);
	}

	@DeleteMapping("{postId}")
	@LoginCheck(userType = UserType.USER)
	public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePosts(String accountId,
		@PathVariable("postId") int postId,
		@RequestBody PostDeleteRequest postDeleteRequest) {

		UserDTO userDTO = userService.getUserInfo(accountId);
		postService.deletePosts(userDTO.getId(), postId);
		CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePosts", postDeleteRequest);
		return ResponseEntity.ok(commonResponse);
	}

	// -- comments --

	@PostMapping("comments")
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(userType = UserType.USER)
	public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(String accountId, @RequestBody CommentDTO commentDTO) {
//		postService.registerComment()
		return null;
	}

	// --- response 객체 ---
	@Getter
	@AllArgsConstructor
	private static class PostResponse {
		private List<PostDTO> postDTOs;
	}

	// --- request 객체 ---
	@Getter
	@Setter
	private static class PostRequest {
		private String name;
		private String contents;
		private int views;
		private int categoryId;
		private int userId;
		private int fileId;
		private LocalDateTime updateTime;
	}

	@Getter
	@Setter
	private static class PostDeleteRequest {
		private int id;
		private int accountId;
	}

}
