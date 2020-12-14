package com.weilyu.springsecuritywithauth0.web;


import com.weilyu.springsecuritywithauth0.model.Message;
import com.weilyu.springsecuritywithauth0.utils.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;

import java.util.Optional;

import static com.weilyu.springsecuritywithauth0.utils.SecurityUtils.getCurrentUserLogin;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class APIController {
    @GetMapping(value = "/public")
    public Message publicEndpoint() {
        return new Message("You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public Message privateEndpoint() {
        Optional<String> loginInfo = SecurityUtils.getCurrentUserLogin();
        return new Message("You can see this because you are Authenticated.  " + "Token Claims: " + loginInfo);
    }

    @GetMapping(value = "/private-scoped")
    public Message privateScopedEndpoint() {
        return new Message("You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
