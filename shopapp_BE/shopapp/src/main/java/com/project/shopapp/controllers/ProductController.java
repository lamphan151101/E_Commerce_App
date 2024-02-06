package com.project.shopapp.controllers;

import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.InvalidParamException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.IProductService;
import com.project.shopapp.services.ProductService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
  private final IProductService productService;
  @GetMapping("")
  public ResponseEntity<List<ProductListResponse>> getProducts(
    @RequestParam("page") int page,
    @RequestParam("limit") int limit
  ){
    PageRequest pageRequest = PageRequest.of(page, limit,
      Sort.by("createdAt").descending());
    Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
    int totalPages = productPage.getTotalPages();
    List<ProductResponse> products = productPage.getContent();
    return ResponseEntity.ok(Collections.singletonList(ProductListResponse.builder().products(products).totalPages(totalPages).build()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
    try{
      Product existingProduct = productService.getProductById(productId);
      return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
    } catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProductById(@PathVariable("id") Long productId){
    try{
      productService.deleteProduct(productId);
      return ResponseEntity.ok("Delete successfully");
    } catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  private String storeFile(MultipartFile file) throws IOException{
    if(!isImageFile(file) || file.getOriginalFilename() == null){
      throw new IOException("Invalid image format");
    }
    String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
    java.nio.file.Path uploadDir = Paths.get("upload");
    if(!Files.exists(uploadDir)){
      Files.createDirectories(uploadDir);
    }
    java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

    Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
    return uniqueFilename;
  }

  private boolean isImageFile(MultipartFile file){
    String contentType = file.getContentType();
    return contentType != null && contentType.startsWith("image/");
  }

  @PostMapping("")
  public ResponseEntity<?> createProduct(
    @Valid @RequestBody ProductDTO productDTO,
    BindingResult result){
    try{
      if(result.hasErrors()){
        List<String> errorMessage = result.getFieldErrors()
          .stream()
          .map(FieldError::getDefaultMessage)
          .toList();
        return ResponseEntity.badRequest().body(errorMessage);
      }
      Product newProduct = productService.createProduct(productDTO);

      return ResponseEntity.ok(newProduct);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImages(
    @PathVariable("id") Long productId,
    @ModelAttribute("files") List<MultipartFile> files
  ){
      try {
          Product existingProduct = productService.getProductById(productId);
        files = files == null ? new ArrayList<MultipartFile>() : files;
        if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
          return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
        }
        List<ProductImage> productImages = new ArrayList<>();
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
          ProductImage productImage = productService.createProductImage(
            existingProduct.getId(),
            ProductImageDTO.builder()
              .imageUrl(filename)
              .build()
          );
          productImages.add(productImage);
        }
        return ResponseEntity.ok().body(productImages);
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PostMapping("/generateFakeProducts")
  public ResponseEntity<String> generateFakeProducts() {
    Faker faker = new Faker();
    for(int i = 0; i < 1000000; i++){
      String productName = faker.commerce().productName();
      if(productService.existsByName(productName)){
        continue;
      }
      ProductDTO productDTO = ProductDTO.builder()
        .name(productName)
        .price((float)faker.number().numberBetween(10, 90000000))
        .description(faker.lorem().sentence())
        .thumbnail("")
        .categoryId((long)faker.number().numberBetween(2, 6))
        .build();
      try{
        productService.createProduct(productDTO);
      } catch(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    return ResponseEntity.ok("Fake Products created successfully");
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProduct(
    @PathVariable long id,
    @RequestBody ProductDTO productDTO
  ){
    try{
      Product updatedProduct = productService.updateProduct(id, productDTO);
      return ResponseEntity.ok(updatedProduct);
    } catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
