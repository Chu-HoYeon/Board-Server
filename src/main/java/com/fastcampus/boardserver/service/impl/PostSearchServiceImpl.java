package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

	@Autowired
	private PostSearchMapper postSearchMapper;

	@Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
	@Override
	public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
		List<PostDTO> postDTOList = null;
		try {
			postDTOList = postSearchMapper.getPosts(postSearchRequest);
		} catch (RuntimeException e) {
			log.error("selectPosts 메서드 실패", e.getMessage());
		}
		return postDTOList;
	}
}
