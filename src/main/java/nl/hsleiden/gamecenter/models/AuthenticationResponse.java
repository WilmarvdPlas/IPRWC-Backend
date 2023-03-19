package nl.hsleiden.gamecenter.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationResponse {

    String token;
    Account account;

}
