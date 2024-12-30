package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

	void insertCategory (CategoryDTO categoryDTO);

	void updateCategory (CategoryDTO categoryDTO);

	void deleteCategory (int categoryId);
}
