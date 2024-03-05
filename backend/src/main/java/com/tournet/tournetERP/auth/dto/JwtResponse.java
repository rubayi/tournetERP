package com.tournet.tournetERP.auth.dto;
/**
 * Spring Security
 *
 * @author : rubayi
 * @fileName : JwtResponse
 * @since : 2024-02-14
 */
import java.util.List;
import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String empEmail;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken,
                       Long id, String username, String empEmail, List<String> roles) {
        this.token = accessToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.empEmail = empEmail;
        this.roles = roles;
    }


}