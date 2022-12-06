package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.AccountDAO;
import nl.hsleiden.gamecenter.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/account")
public class AccountController {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountDAO.getAccounts();
    }

}
