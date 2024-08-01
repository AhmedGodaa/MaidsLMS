package cc.maids.lms.controllers.admin;

import cc.maids.lms.model.User;
import cc.maids.lms.model.dto.MakeUserAdminRequest;
import cc.maids.lms.model.dto.MessageResponse;
import cc.maids.lms.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<MessageResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }


    @PostMapping("/authorities/add")
    public ResponseEntity<User> makeUserAdmin(@RequestBody MakeUserAdminRequest makeUserAdminRequest) {
        return ResponseEntity.ok(userService.makeUserAdmin(makeUserAdminRequest));
    }

}
