package cc.maids.lms.services;

import cc.maids.lms.common.Constants;
import cc.maids.lms.config.userInstance.UserDetailsImpl;
import cc.maids.lms.config.utils.JwtUtils;
import cc.maids.lms.model.User;
import cc.maids.lms.model.dto.*;
import cc.maids.lms.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    //    Register User


    public User createUser(CreateUserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail().toLowerCase());
        }
        user.setCreatedAt(LocalDateTime.now().toString());
        user.setUpdatedAt(LocalDateTime.now().toString());
        user.setCity("City");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setAuthorities(
                new ArrayList<>(List.of(Constants.USER_ROLE))

        );
        return userRepository.save(user);

    }


    public MessageResponse<UserSignUpResponse> signup(CreateUserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail().toLowerCase()).isPresent()) {
            throw new RuntimeException("Email Already In Use");
        } else {
            User user = createUser(userRequest);
            UserSignUpResponse userSignUpResponse = new UserSignUpResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getCreatedAt(),
                    user.getUpdatedAt(),
                    user.getAddress(),
                    user.getCity(),
                    user.getPhone(),
                    false,
                    false,
                    user.getAuthorities()

            );
            return new MessageResponse<>("User Signup Successfully", userSignUpResponse);
        }
    }


    public MessageResponse<UserSignInResponse> signIn(UserSignInRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
            logger.info(authentication.toString());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        logger.info(userRequest.getEmail() + "a" + userRequest.getPassword() + "a");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        logger.info(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.info(userDetails.toString());
        String jwt = jwtUtils.generateJwtToken(userDetails);
        logger.info(jwt);

        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new MessageResponse<>("User SignIn Successfully", new UserSignInResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                jwt,
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getAddress(),
                user.getCity(),
                user.getPhone(),
                false,
                false,
                user.getAuthorities()));

    }

    public MessageResponse<List<User>> getAllUser() {
        return new MessageResponse<>("Get All Users Successfully", userRepository.findAll());
    }

    public User makeUserAdmin(MakeUserAdminRequest makeUserAdminRequest) {
        if (makeUserAdminRequest == null) {
            throw new RuntimeException("Request can't be empty");
        }

        if (makeUserAdminRequest.getEmail().isEmpty() || makeUserAdminRequest.getEmail().isBlank()) {
            throw new RuntimeException("Email can't be empty");
        }
        if (makeUserAdminRequest.getAuthorities().isEmpty()) {
            throw new RuntimeException("Please Add Authorities");

        }
        User user = userRepository.findByEmail(makeUserAdminRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getAuthorities().addAll(makeUserAdminRequest.getAuthorities());
        user.setUpdatedAt(LocalDateTime.now().toString());
        return userRepository.save(user);
    }
}
