package by.zelenko.numerologic.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Table(name = "clients")
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty(message = "Введите Имя")
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @NotEmpty(message = "Введите фамилию")
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_day")
    private LocalDate birthDay;
    @ManyToOne(fetch = FetchType.EAGER)
    //Не удалялось со значением nullable = false
    @JoinColumn(name = "user_id", nullable = true)
    private User user;


    public Client() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(birthDay, client.birthDay) &&
                Objects.equals(user, client.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDay, user);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", user=" + user +
                '}';
    }
}
