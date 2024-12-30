package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

	private final CategoryMapper categoryMapper;

	public CategoryServiceImpl(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Override
	public void register(String accountId, CategoryDTO categoryDTO) {
		if (accountId != null) {
			categoryMapper.insertCategory(categoryDTO);
		} else {
			log.error("insertCategory ERROR! {}", categoryDTO);
			throw new RuntimeException("insertCategory ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
		}
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		if (categoryDTO != null) {
			categoryMapper.updateCategory(categoryDTO);
		} else {
			log.error("updateCategory ERROR! {}", categoryDTO);
			throw new RuntimeException("updateCategory ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
		}
	}

	@Override
	public void delete(int categoryId) {
		if (categoryId != 0) {
			categoryMapper.deleteCategory(categoryId);
		} else {
			log.error("deleteCategory ERROR! {}", categoryId);
			throw new RuntimeException("deleteCategory ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요" + categoryId);
		}
	}
}
