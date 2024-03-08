package com.tournet.tournetERP.security.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : JwtResponse
 * @since : 2024-03-07
 */
import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    // getters and setters
}
