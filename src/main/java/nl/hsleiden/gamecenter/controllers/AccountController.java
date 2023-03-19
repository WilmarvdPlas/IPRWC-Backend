package nl.hsleiden.gamecenter.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.DAOs.AccountDAO;
import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.services.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/account")
public class AccountController {

    private final AccountDAO accountDAO;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

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

    private boolean senderIsOwner(String token, UUID id) throws EntityNotFoundException {
        boolean senderIsOwner;

        String senderEmail = jwtService.extractEmail(token);

        Account senderAccount = accountDAO.findAccountByEmail(senderEmail).orElseThrow(EntityNotFoundException::new);
        senderIsOwner = senderAccount.getId().equals(id);

        return senderIsOwner;
    }

    private boolean senderIsAdministrator(String token) throws EntityNotFoundException {
        boolean senderIsAdministrator;

        String senderEmail = jwtService.extractEmail(token);

        Account senderAccount = accountDAO.findAccountByEmail(senderEmail).orElseThrow(EntityNotFoundException::new);
        senderIsAdministrator = senderAccount.getAdministrator();

        return senderIsAdministrator;
    }

    @PutMapping(path = "{id}/administrator")
    public ResponseEntity<Object> setAdministrator(@PathVariable UUID id) {
        this.accountDAO.setAdministrator(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> setArchived(@PathVariable UUID id, @RequestHeader("authorization") String token) {
        token = token.substring(7);

        boolean senderIsOwner;
        boolean senderIsAdministrator;

        try {
            senderIsOwner = senderIsOwner(token, id);
            senderIsAdministrator = senderIsAdministrator(token);
        } catch (EntityNotFoundException exc) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }

        if (!senderIsAdministrator && !senderIsOwner) {
            return new ResponseEntity<>("Only administrators, or the owner of an account are authorised to delete an account.", HttpStatus.FORBIDDEN);
        }

        if (accountDAO.getAccount(id).isPresent() && accountDAO.getAccount(id).get().getAdministrator() && !senderIsOwner) {
            return new ResponseEntity<>("Administrator accounts can only be deleted by their owners.", HttpStatus.FORBIDDEN);
        }

        accountDAO.setArchived(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> editAccount(@PathVariable UUID id, @RequestHeader("authorization") String token, @RequestBody Account account) {
        token = token.substring(7);

        boolean senderIsOwner;

        try {
            senderIsOwner = senderIsOwner(token, id);
        } catch (EntityNotFoundException exc) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }

        if (!senderIsOwner) {
            return new ResponseEntity<>("Accounts can only be edited by their owners.", HttpStatus.FORBIDDEN);
        }

        accountDAO.findAccountById(account.getId()).ifPresent(value -> account.setPassword(value.getPassword()));

        accountDAO.saveAccount(account);

        String newToken = jwtService.generateToken(account.getEmail());
        HashMap<Object, Object> responseBody = new HashMap<>();
        responseBody.put("token", newToken);

        return new ResponseEntity<>(responseBody ,HttpStatus.OK);
    }

    @PutMapping(path = "{id}/change_password")
    public ResponseEntity<Object> changePassword(@PathVariable UUID id, @RequestBody HashMap<String, String> passwords, @RequestHeader("authorization") String token ) {
        token = token.substring(7);

        boolean senderIsOwner;

        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        try {
            senderIsOwner = senderIsOwner(token, id);
        } catch (EntityNotFoundException exc) {
            return new ResponseEntity<>("Entity could not be found.", HttpStatus.NOT_FOUND);
        }

        if (!senderIsOwner) {
            return new ResponseEntity<>("Passwords can only be changed by their owners.", HttpStatus.FORBIDDEN);
        }

        Optional<Account> optionalAccount = accountDAO.findAccountById(id);
        if (optionalAccount.isEmpty()) {
            return new ResponseEntity<>("Account with id '" + id + "' does not exist.", HttpStatus.NOT_FOUND);
        }
        Account account = optionalAccount.get();

        if (!passwordEncoder.matches(oldPassword ,optionalAccount.get().getPassword())) {
            return new ResponseEntity<>("Credentials are incorrect.", HttpStatus.UNAUTHORIZED);
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        accountDAO.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
