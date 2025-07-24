package Think41.message.service;


import Think41.message.exception.exceptions.ConnectionNotFoundException;
import Think41.message.exception.exceptions.DuplicateRequestException;
import Think41.message.exception.exceptions.InvalidRequestException;
import Think41.message.exception.exceptions.UserNotFoundException;
import Think41.message.model.Connection;
import Think41.message.model.User;
import Think41.message.repository.ConnectionRepository;
import Think41.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ConnectionService {
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipService friendshipService;

    @Transactional
    public Connection sendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException(receiverId));

        Optional<Connection> existing = connectionRepository.findBySenderAndReceiver(sender, receiver);
        if (existing.isPresent()) {
            throw new DuplicateRequestException("Request already sent");
        }

        Connection connection = new Connection(sender, receiver);
        return connectionRepository.save(connection);
    }

    @Transactional
    public void acceptRequest(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ConnectionNotFoundException(connectionId));

        if (connection.getStatus() != Connection.Status.PENDING) {
            throw new InvalidRequestException("Request already processed");
        }

        connection.setStatus(Connection.Status.ACCEPTED);
        connectionRepository.save(connection);
        friendshipService.addFriendship(
                connection.getSender().getId(),
                connection.getReceiver().getId()
        );
    }

    @Transactional
    public void rejectRequest(Long connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ConnectionNotFoundException(connectionId));

        if (connection.getStatus() != Connection.Status.PENDING) {
            throw new InvalidRequestException("Request already processed");
        }

        connection.setStatus(Connection.Status.REJECTED);
        connectionRepository.save(connection);
    }
}