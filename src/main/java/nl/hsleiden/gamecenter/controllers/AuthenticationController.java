package nl.hsleiden.gamecenter.controllers;

import lombok.RequiredArgsConstructor;
import nl.hsleiden.gamecenter.models.AuthenticationRequest;
import nl.hsleiden.gamecenter.models.AuthenticationResponse;
import nl.hsleiden.gamecenter.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> loginHandler(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

            return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
        }
        catch (AuthenticationException authExc) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Invalid Login Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
}
