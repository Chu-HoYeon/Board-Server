package com.fastcampus.boardserver.dto.request;

import com.fastcampus.boardserver.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
	private int id;
	private String name;
	private String contents;
	private int views;
	private int categoryId;
	private int userId;
	private CategoryDTO.SortStatus sortStatus;
}
