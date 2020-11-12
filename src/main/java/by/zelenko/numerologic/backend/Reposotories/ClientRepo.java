package by.zelenko.numerologic.backend.Reposotories;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ClientRepo extends JpaRepository<Client, Long > {

    @Query("select c from Client c " +
            "where c.user=:user AND lower(c.firstName) like lower(concat('%', :searchTerm, '%'))") //
    List<Client> search(@Param("searchTerm") String searchTerm, @Param("user")User user);
    List<Client> findClientsByUserId(Long id);
}
