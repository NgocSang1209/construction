package com.project.webxaydung.Controllers;

import com.project.webxaydung.Dto.UpdateUserDTO;
import com.project.webxaydung.Dto.UserDTO;
import com.project.webxaydung.Dto.UserLoginDTO;
import com.project.webxaydung.Models.SubEmail;
import com.project.webxaydung.Models.User;
import com.project.webxaydung.Responses.LoginResponse;
import com.project.webxaydung.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")

    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try{
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User user = userService.createUser(userDTO);
            //return ResponseEntity.ok("Register successfully");
            return ResponseEntity.ok(user);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(
                    userLoginDTO.getAccount(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRole_id() == null ? 1 : userLoginDTO.getRole_id()
            );
            // Trả về token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Login is successfully!")
                    .token(token)
                    .role_id(userLoginDTO.getRole_id())
                    .build());
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Login is failed")
                            .build()
            );
        }
    }
    @GetMapping("")// http://localhost:8080/api/v1/subemail
    public ResponseEntity<List<User>> getAllUser(){

        List<User> users=userService.getAllUser();
        return  ResponseEntity.ok(users);
    }
    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String extractedToken = authorizationHeader.substring(7); // Loại bỏ "Bearer " từ chuỗi token
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/details/{userId}")
    public ResponseEntity<?> updateUserDetails(
            @PathVariable int userId,
            @RequestBody UpdateUserDTO updatedUserDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            // Ensure that the user making the request matches the user being updated
//            if (user.getId() != userId) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
            User updatedUser = userService.updateUser(userId, updatedUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete category with id: "+id+" successfully");
    }
}
