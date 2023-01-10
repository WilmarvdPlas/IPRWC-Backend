package nl.hsleiden.gamecenter.services;

import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class LoggedInUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public LoggedInUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountRes = accountRepository.findAccountByEmail(email);
        if(accountRes.isEmpty())
            throw new UsernameNotFoundException("Could not find user with email = " + email);
        Account account = accountRes.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + (account.getAdministrator() ? "ADMINISTRATOR" : "USER"))));
    }
}