package Think41.message.repository;

import Think41.message.model.Connection;
import Think41.message.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Optional<Connection> findBySenderAndReceiver(User sender, User receiver);
}