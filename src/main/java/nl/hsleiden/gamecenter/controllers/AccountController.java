package nl.hsleiden.gamecenter.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import nl.hsleiden.gamecenter.DAOs.AccountDAO;
import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/account")
public class AccountController {

    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    @Autowired
    public AccountController(AccountDAO accountDAO, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return new ResponseEntity<>(accountDAO.getAccounts(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Optional<Account>> getAccount(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(accountDAO.getAccount(id), HttpStatus.OK);
    }

    @PostMapping(path = "register")
    public ResponseEntity<Object> registerAccount(@RequestBody Account account) {

        if (account.getAdministrator()) {
            return new ResponseEntity<>("Illegal registration", HttpStatus.UNAUTHORIZED);
        }

        return createAccountWithEmailCheck(account);
    }

    @PostMapping
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        return createAccountWithEmailCheck(account);
    }

    private ResponseEntity<Object> createAccountWithEmailCheck(Account account) {
        if (accountDAO.findAccountByEmail(account.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email address is already in use", HttpStatus.CONFLICT);
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountDAO.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean senderIsOwner(String token, UUID id) throws JWTVerificationException {
        boolean senderIsOwner = false;

        String senderEmail = jwtUtil.validateTokenAndRetrieveSubject(token);

        if (accountDAO.findAccountByEmail(senderEmail).isPresent()) {
            Account senderAccount = accountDAO.findAccountByEmail(senderEmail).get();
            senderIsOwner = senderAccount.getId() == id;
        }

        return senderIsOwner;
    }

    private boolean senderIsAdministrator(String token) {
        boolean senderIsAdministrator = false;

        String senderEmail = jwtUtil.validateTokenAndRetrieveSubject(token);

        if (accountDAO.findAccountByEmail(senderEmail).isPresent()) {
            Account senderAccount = accountDAO.findAccountByEmail(senderEmail).get();
            senderIsAdministrator = senderAccount.getAdministrator();
        }

        return senderIsAdministrator;
    }

    @PutMapping(path = "{id}/administrator")
    public ResponseEntity<Object> setAdministrator(@PathVariable UUID id) {
        this.accountDAO.setAdministrator(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable UUID id, @RequestHeader("authorization") String token) {
        token = token.substring(7);

        boolean senderIsOwner;
        boolean senderIsAdministrator;

        try {
            senderIsOwner = senderIsOwner(token, id);
            senderIsAdministrator = senderIsAdministrator(token);

        } catch(JWTVerificationException exc) {
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.BAD_REQUEST);
        }

        if (!senderIsAdministrator && !senderIsOwner) {
            return new ResponseEntity<>("Only administrators, or the owner of an account are authorised to delete an account.", HttpStatus.FORBIDDEN);
        }

        if (accountDAO.getAccount(id).isPresent() && accountDAO.getAccount(id).get().getAdministrator() && !senderIsOwner) {
            return new ResponseEntity<>("Administrator accounts can only be deleted by their owners.", HttpStatus.FORBIDDEN);
        }

        accountDAO.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> editAccount(@PathVariable UUID id, @RequestHeader("authorization") String token, @RequestBody Account account) {
        boolean senderIsOwner;

        try {
            senderIsOwner = senderIsOwner(token, id);
        } catch(JWTVerificationException exc) {
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.BAD_REQUEST);
        }

        if (!senderIsOwner) {
            return new ResponseEntity<>("Accounts can only be edited by their owners.", HttpStatus.FORBIDDEN);
        }

        Optional<Account> optionalAccount = accountDAO.findAccountByEmail(account.getEmail());
        if (optionalAccount.isPresent() && !optionalAccount.get().getPassword().equals(account.getPassword())) {
            return new ResponseEntity<>("Passwords can only be altered through the change_password endpoint.", HttpStatus.FORBIDDEN);
        }

        accountDAO.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
