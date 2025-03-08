package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

	private final PostMapper postMapper;
	private final UserProfileMapper userProfileMapper;

	public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper) {
		this.postMapper = postMapper;
		this.userProfileMapper = userProfileMapper;
	}

	@Override
	public void register(String id, PostDTO postDTO) {
		UserDTO userDTO = userProfileMapper.selectUserProfile(id);
		postDTO.setUserId(userDTO.getId());
		postDTO.setCreateTime(LocalDateTime.now());
		postDTO.setUpdateTime(LocalDateTime.now());

		if (userDTO != null) {
			postMapper.register(postDTO);
		} else {
			log.error("register ERROR! {}", postDTO);
			throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요. " + postDTO);
		}
	}

	@Override
	public List<PostDTO> getMyPosts(int accountId) {
		return postMapper.selectMyPosts(accountId);
	}

	@Override
	public void updatePosts(PostDTO postDTO) {
		if (postDTO != null && postDTO.getUserId() != 0) {
			postMapper.updatePosts(postDTO);
		} else {
			log.error("updatePosts ERROR! {}", postDTO);
			throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요. " + postDTO);
		}
	}

	@Override
	public void deletePosts(int userId, int postId) {
		if (userId != 0 && postId != 0) {
			postMapper.deletePosts(postId);
		} else {
			log.error("deletePosts ERROR! {}", postId);
			throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요" + postId);
		}
	}
}
