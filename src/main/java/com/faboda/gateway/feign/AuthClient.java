package com.faboda.gateway.feign;


import com.faboda.gateway.dto.AuthenticationReq;
import com.faboda.gateway.dto.CreateTokenResponse;
import com.faboda.gateway.dto.GetEmailFromTokenRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auth-server", url = "http://localhost:8081")
public interface AuthClient {

    @PostMapping(value = "/auth0/authenticate", consumes = "application/json")
    CreateTokenResponse validate(@RequestBody AuthenticationReq authenticationReq);

    @PostMapping(value="/auth0/getUserEmailFromToken")
    GetEmailFromTokenRequest getUserEmailFromToken(@RequestHeader("Authorization") String token);
}
