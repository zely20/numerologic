package by.zelenko.numerologic.backend.Model.Service;

import by.zelenko.numerologic.backend.Model.Reposotories.UserRepo;
import by.zelenko.numerologic.backend.Model.User;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());

    public UserService(@Autowired UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public List<User> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            LOG.log(Level.DEBUG, "from find");
            return userRepo.findAll();
        } else {
            return userRepo.search(stringFilter);
        }
    }

    public long count() {
        return userRepo.count();
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public void save(User user) {
        if (user == null) {
            LOG.log(Level.DEBUG, "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepo.save(user);
    }
}
