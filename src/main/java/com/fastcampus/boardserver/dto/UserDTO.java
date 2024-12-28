package com.fastcampus.boardserver.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

	public enum Status {
		DEFAULT, ADMIN, DELETED
	}

	private int id;
	private String userId;
	private String password;
	private String nickName;
	private boolean isAdmin;
	private LocalDateTime createTime;
	private boolean isWithDraw;
	private Status status;
	private LocalDateTime updateTime;
}
