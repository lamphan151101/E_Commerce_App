package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.CateGory;
import com.project.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping("")
  // nếu tham số truyền vào là 1 object thì sao? => Data Tranfer Object = Request Object
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
    if (result.hasErrors()) {
      List<String> errorMessage = result.getFieldErrors()
        .stream()
        .map(FieldError::getDefaultMessage)
        .toList();
      return ResponseEntity.badRequest().body(errorMessage);
    }
    categoryService.createCategory(categoryDTO);
    return ResponseEntity.ok("insert category successfully");
  }


    @GetMapping("") //https: //http://localhost:8089/api/v1/categories
    public ResponseEntity<List<CateGory>> getAllCategory(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
    ){
      List<CateGory> categories = categoryService.getAllCategories();
      return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
      categoryService.updateCategory(id, categoryDTO);
      return ResponseEntity.ok("update successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
      categoryService.deleteCategory(id);
      return ResponseEntity.ok("DeleteCategory successfully");
    }
}
