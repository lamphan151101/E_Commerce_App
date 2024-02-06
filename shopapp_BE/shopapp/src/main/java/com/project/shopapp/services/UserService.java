package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Roles;
import com.project.shopapp.models.Users;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  @Override
  public Users createUser(UserDTO userDTO) throws DataNotFoundException {
    String phoneNumber = userDTO.getPhoneNumber();
    if(userRepository.existsByPhoneNumber(phoneNumber)){
      throw new DataIntegrityViolationException("Phone number already exists");
    }
    //convert form userDTO to user
    Users newUser = Users.builder().fullName(userDTO.getFullName())
      .phoneNumber(userDTO.getPhoneNumber())
      .password(userDTO.getPassword())
      .address(userDTO.getAddress())
      .dateOfBirth(userDTO.getDateOfBirth())
      .facebookAccountId(userDTO.getFacebookAccountId())
      .googleAccountId(userDTO.getGoogleAccountId())
      .build();
    Roles role = roleRepository.findById(userDTO.getRoleId())
      .orElseThrow(()-> new DataNotFoundException("Role not found"));
    newUser.setRole(role);

    //Kiểm tra nếu có accountId, không yêu cầu password
    if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
      String password = userDTO.getPassword();
//      String encodedPassword = passwordEncoder.encode(password);
//      newUser.setPassword(encodedPassword);
    }
    return userRepository.save(newUser);
  }

  @Override
  public String login(String phoneNumber, String password) {
    return null;
  }
}
