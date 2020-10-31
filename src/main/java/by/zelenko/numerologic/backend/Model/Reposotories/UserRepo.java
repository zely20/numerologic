package by.zelenko.numerologic.backend.Model.Reposotories;

import by.zelenko.numerologic.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "where lower(u.userName) like lower(concat('%', :searchTerm, '%'))") //
    List<User> search(@Param("searchTerm") String searchTerm);
}
