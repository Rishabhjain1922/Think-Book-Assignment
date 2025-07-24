package Think41.message.controller;


import Think41.message.model.User;
import Think41.message.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {
    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/add")
    public ResponseEntity<Void> addFriends(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        friendshipService.addFriendship(user1Id, user2Id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFriends(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        friendshipService.removeFriendship(user1Id, user2Id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<User>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getFriends(userId));
    }

    @GetMapping("/mutual")
    public ResponseEntity<List<User>> getMutualFriends(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id) {
        return ResponseEntity.ok(friendshipService.getMutualFriends(user1Id, user2Id));
    }

    @GetMapping("/degree")
    public ResponseEntity<Integer> getDegreeOfSeparation(
            @RequestParam Long user1Id,
            @RequestParam Long user2Id) {
        return ResponseEntity.ok(friendshipService.calculateDegreeOfSeparation(user1Id, user2Id));
    }
}