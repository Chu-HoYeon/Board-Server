package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.aop.LoginCheck.UserType;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.dto.CategoryDTO.SortStatus;
import com.fastcampus.boardserver.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

	@Getter
	@Setter
	private static class CategoryRequest {
		private int id;
		private String name;
	}

	private final CategoryServiceImpl categoryService;

	public CategoryController(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(userType = UserType.ADMIN)
	public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
		categoryService.register(accountId, categoryDTO);
	}

	@PatchMapping("{categoryId}")
	@LoginCheck(userType = UserType.ADMIN)
	public void updateCategory(String accountId, @PathVariable("categoryId") int categoryId, @RequestBody CategoryRequest categoryRequest) {
		CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 10);
		categoryService.update(categoryDTO);
	}

	@DeleteMapping("{categoryId}")
	@LoginCheck(userType = UserType.ADMIN)
	public void deleteCategory(String accountId, @PathVariable("categoryId") int categoryId) {
		categoryService.delete(categoryId);
	}

}
