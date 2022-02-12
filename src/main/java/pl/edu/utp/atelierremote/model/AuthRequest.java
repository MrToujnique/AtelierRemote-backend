package pl.edu.utp.atelierremote.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
    private String userName;
    private String password;
}