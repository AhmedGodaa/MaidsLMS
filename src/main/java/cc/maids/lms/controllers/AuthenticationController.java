package cc.maids.lms.controllers;

import cc.maids.lms.model.dto.*;
import cc.maids.lms.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;


    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse<UserSignUpResponse>> signUp(@Valid @RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(userService.signup(userRequest));
    }


    @PostMapping("/signin")
    public ResponseEntity<MessageResponse<UserSignInResponse>> signIn(@Valid @RequestBody UserSignInRequest userRequest) {
        return ResponseEntity.ok(userService.signIn(userRequest));


    }


}
