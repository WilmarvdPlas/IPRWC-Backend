package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.AccountDAO;
import nl.hsleiden.gamecenter.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/account")
public class AccountController {

    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountDAO accountDAO, PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity getAccounts() {
        return new ResponseEntity(accountDAO.getAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getAccount(@PathVariable("id") UUID id) {
        return new ResponseEntity(accountDAO.getAccount(id), HttpStatus.OK);
    }

    @PostMapping(path = "register")
    public ResponseEntity createAccount(@RequestBody Account account) {

        if (accountDAO.findAccountByEmail(account.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email address is already in use", HttpStatus.CONFLICT);
        }

        if (account.getAdministrator()) {
            return new ResponseEntity<>("Illegal registration", HttpStatus.UNAUTHORIZED);
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountDAO.createAccount(account);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

}
