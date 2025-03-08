package com.fastcampus.boardserver.dto;

import java.time.LocalDateTime;
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
public class PostDTO {

	private int id;
	private String name;
	private boolean isAdmin;
	private String contents;
	private LocalDateTime createTime;
	private int views;
	private int categoryId;
	private int userId;
	private int fileId;
	private LocalDateTime updateTime;

}
