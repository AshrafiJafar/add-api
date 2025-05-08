package com.jafar.add_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class DatabaseCredentialService {

    private final SecretsManagerClient secretsClient;
    private final String secretName = "postgres-credential"; //  Name of your secret

    
    public DatabaseCredentialService() {

        this.secretsClient = SecretsManagerClient.builder()
        .region(Region.of("us-east-1"))
        .credentialsProvider(
            System.getenv("AWS_WEB_IDENTITY_TOKEN_FILE") != null 
            ? WebIdentityTokenFileCredentialsProvider.create() 
            : DefaultCredentialsProvider.create()
        )
        .build();
        
        // this.secretsClient = SecretsManagerClient.builder()
        //         .region(Region.of("us-east-1")) //  Configure your AWS region
        //         .build();
    }

    public String getUsername() {
        return getSecretData().get("username").asText();
    }

    public String getPassword() {
        return getSecretData().get("password").asText();
    }

     public String getUrl() {
        return getSecretData().get("url").asText();
    }


    private JsonNode getSecretData() {
        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        GetSecretValueResponse response = secretsClient.getSecretValue(request);
        String secretString = response.secretString();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(secretString);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing secret JSON", e); //  Handle the exception appropriately
        }
    }
}