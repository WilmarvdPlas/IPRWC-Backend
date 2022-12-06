package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.models.Role;
import nl.hsleiden.gamecenter.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDAO {

    private final RoleRepository repository;

    @Autowired
    public RoleDAO(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getRoles() {
        return repository.findAll();
    }

}
