package com.weilyu.springsecuritywithauth0.web;


import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.net.RequestOptions;
import com.weilyu.springsecuritywithauth0.config.StripeConfig;
import com.weilyu.springsecuritywithauth0.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = "*")
public class StripeResource {

    private final StripeConfig stripe;

    public StripeResource(StripeConfig stripe) {
        this.stripe = stripe;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getCustomers() throws StripeException {
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(stripe.getSecretKey())
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        return Customer.list(params, requestOptions).getData();
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createCustomer() throws StripeException {

        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(stripe.getSecretKey())
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put(
                "description",
                "My First Test Customer (created via backend API)"
        );
        params.put(
                "name",
                "Jack"
        );
        params.put(
                "email",
                "jack@api.com"
        );

        Customer customer = Customer.create(params, requestOptions);

        return new Message("Customer: " + customer.getName() + " created");

    }

    @PostMapping("/users/{userId}/card")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createCard(@PathVariable String userId) throws StripeException {
//
//        RequestOptions requestOptions = RequestOptions.builder()
//                .setApiKey(stripe.getSecretKey())
//                .build();

        Map<String, Object> retrieveParams =
                new HashMap<>();
        List<String> expandList = new ArrayList<>();
        expandList.add("sources");
        retrieveParams.put("expand", expandList);

        Customer customer =
                Customer.retrieve(
                        userId,
                        retrieveParams,
                        null
                );

        Map<String, Object> params = new HashMap<>();
        params.put("source", "tok_mastercard");

        Card card = (Card) customer.getSources().create(params);

        return new Message(card.toJson());
    }

}
