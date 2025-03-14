package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.CommentMapper;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.TagMapper;
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
	private final CommentMapper commentMapper;
	private final TagMapper tagMapper;

	public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper,
		CommentMapper commentMapper, TagMapper tagMapper) {
		this.postMapper = postMapper;
		this.userProfileMapper = userProfileMapper;
		this.commentMapper = commentMapper;
		this.tagMapper = tagMapper;
	}

	@Override
	public void register(String id, PostDTO postDTO) {
		UserDTO userDTO = userProfileMapper.selectUserProfile(id);
		postDTO.setUserId(userDTO.getId());
		postDTO.setCreateTime(LocalDateTime.now());
		postDTO.setUpdateTime(LocalDateTime.now());

		if (userDTO != null) {
			postMapper.register(postDTO);
			Integer postId = postDTO.getId();
			for(int i=0; i<postDTO.getTagDTOList().size(); i++) {
				TagDTO tagDTO = postDTO.getTagDTOList().get(i);
				tagMapper.register(tagDTO);
				Integer tagId = tagDTO.getId();
				tagMapper.createPostTag(tagId, postId);
			}
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

	@Override
	public void registerComment(CommentDTO commentDTO) {
		if (commentDTO.getPostId() != 0) {
			commentMapper.register(commentDTO);
		} else {
			log.error("registerComment ERROR! {}", commentDTO);
			throw new RuntimeException("registerComment ERROR! " + commentDTO);
		}
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		if (commentDTO != null) {
			commentMapper.updateComments(commentDTO);
		} else {
			log.error("updateComment ERROR!");
			throw new RuntimeException("updateComment ERROR!");
		}
	}

	@Override
	public void deletePostComment(int userId, int commentId) {
		if (userId != 0 && commentId != 0) {
			commentMapper.deletePostComments(commentId);
		} else {
			log.error("deletePostComment ERROR! {}", commentId);
			throw new RuntimeException("deletePostComment ERROR! " + commentId);
		}
	}

	@Override
	public void registerTag(TagDTO tagDTO) {
		if (tagDTO != null) {
			tagMapper.register(tagDTO);
		} else {
			log.error("registerTag ERROR!");
			throw new RuntimeException("registerTag ERROR!");
		}
	}

	@Override
	public void updateTag(TagDTO tagDTO) {
		if (tagDTO != null) {
			tagMapper.updateTags(tagDTO);
		} else {
			log.error("updateTag ERROR!");
			throw new RuntimeException("updateTag ERROR!");
		}
	}

	@Override
	public void deletePostTag(int userId, int tagId) {
		if (userId != 0 && tagId != 0) {
			tagMapper.deletePostTag(tagId);
		} else {
			log.error("deletePostTag ERROR! {}", tagId);
			throw new RuntimeException("deletePostTag ERROR! " +  tagId);
		}
	}
}
