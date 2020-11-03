package by.zelenko.numerologic.backend.Service;

import by.zelenko.numerologic.backend.Entity.Client;
import by.zelenko.numerologic.backend.Entity.User;
import by.zelenko.numerologic.backend.Reposotories.ClientRepo;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Client> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            LOG.log(Level.DEBUG, "from find");
            return clientRepo.findAll();
        } else {
            return clientRepo.search(stringFilter);
        }
    }

    public long count() {
        return clientRepo.count();
    }

    public void delete(Client client) {
        LOG.log(Level.DEBUG, "from ClientServise delete");
        System.out.println(client);
        clientRepo.delete(client);
    }

    public void save(Client client) {
        if (client == null) {
            LOG.log(Level.DEBUG, "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        if(client.getUser() == null){
            User user = userService.findByUsername("Alex");
            client.setUser(user);
        }
        clientRepo.save(client);
    }

    public Client geyById(Long id){
        return clientRepo.getOne(id);
    }
}
