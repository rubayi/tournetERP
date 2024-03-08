package com.tournet.tournetERP.security.dto;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : TokenRefreshResponse
 * @since : 2024-03-07
 */
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    // getters and setters
}
