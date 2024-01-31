package com.faboda.gateway.controller;

import com.faboda.gateway.dto.AuthenticationReq;
import com.faboda.gateway.dto.CreateTokenResponse;
import com.faboda.gateway.feign.AuthClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class GateController {

    private final AuthClient authClient;

    public GateController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @GetMapping
    public String health(){
        return "Everything seems fine";
    }

    @PostMapping("/login")
    public CreateTokenResponse login(@RequestBody AuthenticationReq authenticationReq){
        return authClient.validate(authenticationReq);
    }
}
