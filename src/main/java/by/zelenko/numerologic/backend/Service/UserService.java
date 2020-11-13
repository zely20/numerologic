package by.zelenko.numerologic.backend.Service;

import by.zelenko.numerologic.backend.Entity.User;
import by.zelenko.numerologic.backend.Reposotories.UserRepo;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());

    public UserService(@Autowired UserRepo userRepo, @Autowired PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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
        LOG.log(Level.DEBUG, "from UserServise delete");
        userRepo.delete(user);
    }

    public void save(User user) {
        if (user == null) {
            LOG.log(Level.DEBUG, "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        Pattern pattern = Pattern.compile("\\$2[aby]");
        Matcher matcher = pattern.matcher(user.getPassword());
        if(!matcher.lookingAt()){
            String pass = passwordEncoder.encode(user.getPassword());
            user.setPassword(pass);
        }
        userRepo.save(user);
    }

    public User findByUsername (String username){
        return userRepo.findByUsername(username).get();
    }
}
