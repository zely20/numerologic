package by.zelenko.numerologic;

import by.zelenko.numerologic.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Long, User> {
    Optional<User> findByUserName(String userName);
}
