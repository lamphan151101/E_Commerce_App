package com.project.shopapp.controllers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.userLoginDTO;
import com.project.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class userController {
  private final UserService userService;
  @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
      try{
        if(result.hasErrors()){
          List<String> errorMessage = result.getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
          return ResponseEntity.badRequest().body(errorMessage);
        }
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
          return ResponseEntity.badRequest().body("wrong password");
        }
        userService.createUser(userDTO);
        return ResponseEntity.ok("Register Successfully");
      } catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
    }

  @PostMapping("/login")
  public ResponseEntity<?> createUser(@Valid @RequestBody userLoginDTO userLoginDTO){
    try{
      String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
      return ResponseEntity.ok(token);
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
