package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.RoleDAO;
import nl.hsleiden.gamecenter.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/role")
public class RoleController {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleController(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @GetMapping
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

}
