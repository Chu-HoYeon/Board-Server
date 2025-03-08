package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.PostDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

	int register(PostDTO postDTO);

	List<PostDTO> selectMyPosts(int accountId);

	int updatePosts(PostDTO postDTO);

	int deletePosts(int postId);

}
