package by.zelenko.numerologic.backend.Service;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Entity.User;
import by.zelenko.numerologic.backend.Reposotories.ClientRepo;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final UserService userService;
    private static final Logger LOG = LogManager.getLogger(ClientService.class.getName());

    @Autowired
    public ClientService(ClientRepo clientRepo, UserService userService) {
        this.clientRepo = clientRepo;
        this.userService = userService;
    }

    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    public List<Client> findByUserName(Long id) {
        return clientRepo.findClientsByUserId(id);
    }

    public List<Client> findAll(String stringFilter,  User user) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            LOG.log(Level.DEBUG, "from find");
            return clientRepo.findClientsByUserId(user.getId());
        } else {
            return clientRepo.search(stringFilter,user);
        }
    }

    public long count() {
        return clientRepo.count();
    }

    public void delete(Client client) {
        clientRepo.delete(client);
    }

    public void save(Client client) {
        if (client == null) {
            LOG.log(Level.DEBUG, "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        if(client.getUser() == null){
            Authentication name = SecurityContextHolder.getContext().getAuthentication();
            name.getName();
            User user = userService.findByUsername(name.getName());
            client.setUser(user);
        }
        clientRepo.save(client);
    }

    public Client geyById(Long id){
        return clientRepo.getOne(id);
    }
}
