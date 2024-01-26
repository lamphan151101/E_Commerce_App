package com.project.shopapp.controllers;

import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    //hien thi tat ca cac category
    @GetMapping("") //https: //http://localhost:8089/api/v1/categories
    public ResponseEntity<String> getAllCategory(
      @RequestParam("page") int page,
      @RequestParam("limit") int limit
    ){
      return ResponseEntity.ok(String.format("getAllCategories, page = %d, limit = %d", page, limit));
    }

    @PostMapping("")
    // nếu tham số truyền vào là 1 object thì sao? => Data Tranfer Object = Request Object
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
      if(result.hasErrors()){
        List<String> errorMessage = result.getFieldErrors()
          .stream()
          .map(FieldError::getDefaultMessage)
          .toList();
        return ResponseEntity.badRequest().body(errorMessage);
      }
      return ResponseEntity.ok("This is insert");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
      return ResponseEntity.ok("This is updateCategory with id =" + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
      return ResponseEntity.ok("DeleteCategory with id = " + id);
    }
}
