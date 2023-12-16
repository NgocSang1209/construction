package com.project.webxaydung.Services;

import com.project.webxaydung.Dto.UpdateUserDTO;
import com.project.webxaydung.Dto.UserDTO;
import com.project.webxaydung.Models.Employee;
import com.project.webxaydung.Models.Role;
import com.project.webxaydung.Models.User;
import com.project.webxaydung.Repositories.EmployeeRepository;
import com.project.webxaydung.Repositories.RoleRepository;
import com.project.webxaydung.Repositories.UserRepository;
import com.project.webxaydung.configurations.components.JwtTokenUtils;
import com.project.webxaydung.exception.DataNotFoundException;
import com.project.webxaydung.exception.PermissionDenyException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
   // private final LocalizationUtils localizationUtils;
   public User createUser(UserDTO userDTO) throws Exception {
       //register user
       String account = userDTO.getAccount();
       // Kiểm tra xem account đã tồn tại hay chưa
       if(userRepository.existsByAccount(account)) {
           throw new DataIntegrityViolationException("Account name already exists");
       }
       Role role =roleRepository.findById(userDTO.getRole_id())
               .orElseThrow(() -> new DataNotFoundException("Role not found"));
       if(role.getName().toUpperCase().equals(Role.ADMIN)) {
           throw new PermissionDenyException("You cannot register an admin account");
       }
       Employee employee =employeeRepository.findById(userDTO.getEmployeeId())
               .orElseThrow(() -> new DataNotFoundException("Employee not found"));
       //convert from userDTO => user
       User newUser = User.builder()
               .account(userDTO.getAccount())
               .password(userDTO.getPassword())
               .active(true)
               .build();
        newUser.setRole(role);
       String password = userDTO.getPassword();
       String encodedPassword = passwordEncoder.encode(password);
       newUser.setPassword(encodedPassword);
       return userRepository.save(newUser);
   }
    public String login(String account, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByAccount(account);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid account / password");
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong account or password");
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException("Role dose not exits");
        }
        if(!optionalUser.get().isActive()) {
            throw new DataNotFoundException("User is locked");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                account, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
    @Transactional
    public User updateUser(int userId, UpdateUserDTO updatedUserDTO) throws Exception {
        // Find the existing user by userId
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // Check if the account is being changed and if it already exists for another user
        String newAccount = updatedUserDTO.getAccount();
        if (!existingUser.getAccount().equals(newAccount) &&
                userRepository.existsByAccount(newAccount)) {
            throw new DataIntegrityViolationException("Account already exists");
        }

        // Update user information based on the DTO
        if (updatedUserDTO.getAccount() != null) {
            existingUser.setAccount(updatedUserDTO.getAccount());
        }

        // Update the password if it is provided in the DTO
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            String newPassword = updatedUserDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setPassword(encodedPassword);
        }
        //existingUser.setRole(updatedRole);
        // Save the updated user
        return userRepository.save(existingUser);
    }

    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)) {
            throw new Exception("Token is expired");
        }
        String account = jwtTokenUtil.extractAccount(token);
        Optional<User> user = userRepository.findByAccount(account);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }
    @Transactional
    public void deleteUser(int id) throws Exception {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        existingUser.setActive(false);
    }
}
