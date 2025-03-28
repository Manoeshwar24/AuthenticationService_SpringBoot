package com.example.authenticationservice.security.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`client`")
public class Client {
    @Id
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @Lob
    private String clientAuthenticationMethods;
    @Lob
    private String authorizationGrantTypes;
    @Lob
    private String redirectUris;
    @Lob
    private String postLogoutRedirectUris;
    @Lob
    private String scopes;
    @Lob
    private String clientSettings;
    @Lob
    private String tokenSettings;
}