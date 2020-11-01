package by.zelenko.numerologic.backend.Service;

import by.zelenko.numerologic.backend.Entity.Client;
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
    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());

    public ClientService(@Autowired ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
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
        clientRepo.delete(client);
    }

    public void save(Client client) {
        if (client == null) {
            LOG.log(Level.DEBUG, "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        clientRepo.save(client);
    }
}
