package nl.hsleiden.gamecenter.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {

    private String token;
    private Account account;

}
