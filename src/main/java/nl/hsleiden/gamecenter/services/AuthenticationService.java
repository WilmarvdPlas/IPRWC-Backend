package nl.hsleiden.gamecenter.services;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.models.AuthenticationResponse;
import nl.hsleiden.gamecenter.models.AuthenticationRequest;
import nl.hsleiden.gamecenter.repositories.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository repository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        Account account = repository.findAccountByEmail(authenticationRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(account.getEmail());

        return new AuthenticationResponse(jwtToken, account);
    }
}
