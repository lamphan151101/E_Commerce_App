package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.CateGory;

import java.util.List;

public interface ICategoryService {
  CateGory createCategory(CategoryDTO category);
  CateGory getCategoryById(long id);
  List<CateGory> getAllCategories();
  CateGory updateCategory(long categoryId, CategoryDTO category);
  void deleteCategory(long id);
}
