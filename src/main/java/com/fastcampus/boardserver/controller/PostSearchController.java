package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.service.impl.PostSearchServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

	private final PostSearchServiceImpl postSearchService;

	@PostMapping
	public PostSerchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
		List<PostDTO> postDTOList = postSearchService.getPosts(postSearchRequest);
		return new PostSerchResponse(postDTOList);
	}

	// -- response 객체 --

	@Getter
	@AllArgsConstructor
	private static class PostSerchResponse {
		private List<PostDTO> postDTOList;
	}
}
