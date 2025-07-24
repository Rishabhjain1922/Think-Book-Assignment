package Think41.message.service;


import Think41.message.exception.exceptions.DuplicateFriendshipException;
import Think41.message.exception.exceptions.NoConnectionException;
import Think41.message.exception.exceptions.UserNotFoundException;
import Think41.message.model.Friendship;
import Think41.message.model.User;
import Think41.message.repository.FriendshipRepository;
import Think41.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Transactional
    public void addFriendship(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        if (friendshipRepository.existsByUserAndFriend(user1, user2)) {
            throw new DuplicateFriendshipException("Friendship already exists");
        }

        friendshipRepository.save(new Friendship(user1, user2));
        friendshipRepository.save(new Friendship(user2, user1));
    }

    @Transactional
    public void removeFriendship(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        friendshipRepository.deleteByUserAndFriend(user1, user2);
        friendshipRepository.deleteByUserAndFriend(user2, user1);
    }

    public List<User> getFriends(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return friendshipRepository.findByUserId(userId).stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long user1Id, Long user2Id) {
        Set<Long> friendsOfUser1 = getFriends(user1Id).stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        return getFriends(user2Id).stream()
                .filter(friend -> friendsOfUser1.contains(friend.getId()))
                .collect(Collectors.toList());
    }

    public int calculateDegreeOfSeparation(Long sourceId, Long targetId) {
        userRepository.findById(sourceId).orElseThrow(() -> new UserNotFoundException(sourceId));
        userRepository.findById(targetId).orElseThrow(() -> new UserNotFoundException(targetId));

        if (sourceId.equals(targetId)) return 1;

        Queue<Long> queue = new LinkedList<>();
        Map<Long, Integer> distances = new HashMap<>();
        queue.add(sourceId);
        distances.put(sourceId, 1);

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            if (current.equals(targetId)) return distances.get(current);

            List<Friendship> friendships = friendshipRepository.findByUserId(current);
            for (Friendship friendship : friendships) {
                Long friendId = friendship.getFriend().getId();
                if (!distances.containsKey(friendId)) {
                    distances.put(friendId, distances.get(current) + 1);
                    queue.add(friendId);
                }
            }
        }
        throw new NoConnectionException("No connection between users");
    }
}