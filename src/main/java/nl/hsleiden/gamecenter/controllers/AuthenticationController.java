package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.AccountDAO;
import nl.hsleiden.gamecenter.models.Account;
import nl.hsleiden.gamecenter.models.LoginCredentials;
import nl.hsleiden.gamecenter.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final AccountDAO accountDAO;

    @Autowired
    public AuthenticationController(JWTUtil jwtUtil, AuthenticationManager authManager, AccountDAO accountDAO) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.accountDAO = accountDAO;
    }

    @PostMapping("/login")
    public ResponseEntity loginHandler(@RequestBody LoginCredentials body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());
            Account account = accountDAO.findAccountByEmail(body.getEmail()).get();

            HashMap<Object, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("account", account);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch (AuthenticationException authExc) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Invalid Login Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
}
