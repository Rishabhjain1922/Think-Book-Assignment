package Think41.message.controller;

;
import Think41.message.model.User;
import Think41.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestParam String name) {
        return ResponseEntity.ok(userService.addUser(name));
    }
}