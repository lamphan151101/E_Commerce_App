package com.project.shopapp.services;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.CateGory;
import com.project.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
  private final CategoryRepository categoryRepository;
//  public CategoryService(CategoryRepository categoryRepository){
//    this.categoryRepository = categoryRepository;
//  }
  @Override
  public CateGory createCategory(CategoryDTO categoryDTO) {
    CateGory newCategory = CateGory
      .builder().name(categoryDTO.getName())
      .build();
    return categoryRepository.save(newCategory);
  }

  @Override
  public CateGory getCategoryById(long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
  }

  @Override
  public List<CateGory> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public CateGory updateCategory(long categoryId, CategoryDTO categoryDTO) {
    CateGory existingCategory = getCategoryById(categoryId);
    existingCategory.setName(categoryDTO.getName());
    categoryRepository.save(existingCategory);
    return existingCategory;
  }

  @Override
  public void deleteCategory(long id) {
    // xóa cứng
    categoryRepository.deleteById(id);
  }
}
