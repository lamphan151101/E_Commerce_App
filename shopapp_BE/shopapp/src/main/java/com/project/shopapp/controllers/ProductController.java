package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
  @GetMapping("")
  public ResponseEntity<String> getAllProducts(
    @RequestParam("page") int page,
    @RequestParam("limit") int limit
  ){
    return ResponseEntity.ok("getProducts here");
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getProductById(@PathVariable("id") String productId){
    return ResponseEntity.ok("getProductById with id =" + productId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProductById(@PathVariable("id") String productId){
    return ResponseEntity.ok("Product Delete successfully");
  }

  private String storeFile(MultipartFile file) throws IOException{
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
    java.nio.file.Path uploadDir = Paths.get("upload");
    if(!Files.exists(uploadDir)){
      Files.createDirectories(uploadDir);
    }
    java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
    return uniqueFilename;
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createProduct(
    @Valid @ModelAttribute ProductDTO productDTO,
//    @RequestPart("file")MultipartFile file,
    BindingResult result){
    try{
      if(result.hasErrors()){
        List<String> errorMessage = result.getFieldErrors()
          .stream()
          .map(FieldError::getDefaultMessage)
          .toList();
        return ResponseEntity.badRequest().body(errorMessage);
      }

      List<MultipartFile> files = productDTO.getFiles();
      files = files == null ? new ArrayList<MultipartFile>() : files;
      for(MultipartFile file : files){
        if(file.getSize() == 0){
          continue;
        }
          if(file.getSize() > 10*1024*1024){
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
          }
          String contentType = file.getContentType();
          if(contentType == null || !contentType.startsWith("image/")){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
          }
          //Luu file va cap nhat thumbnail trong DTO
          String filename = storeFile(file);
        }
      return ResponseEntity.ok("Product created successfully");
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
