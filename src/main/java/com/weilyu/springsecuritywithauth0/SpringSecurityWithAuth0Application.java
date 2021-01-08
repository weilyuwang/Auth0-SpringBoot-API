package com.weilyu.springsecuritywithauth0;

import com.stripe.Stripe;
import com.weilyu.springsecuritywithauth0.config.StripeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyStore;

@SpringBootApplication
public class SpringSecurityWithAuth0Application {

    public SpringSecurityWithAuth0Application(StripeConfig stripeConfig) {
        Stripe.apiKey = stripeConfig.getSecretKey();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityWithAuth0Application.class, args);
    }

}
