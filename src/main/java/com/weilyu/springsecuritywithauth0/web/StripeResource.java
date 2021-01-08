package com.weilyu.springsecuritywithauth0.web;


import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
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

        // Create a Customer:
        CustomerCreateParams customerParams =
                CustomerCreateParams.builder()
                        .setDescription("Customer created via API")
                        .setName("Test Customer")
                        .setSource("tok_mastercard")
                        .setEmail("paying.user@example.com")
                        .build();

        Customer customer = Customer.create(customerParams, requestOptions);

        return new Message("Customer: " + customer.getName() + " created");

    }

    @PostMapping("/users/{userId}/card")
    @ResponseStatus(HttpStatus.CREATED)
    public String createCard(@PathVariable String userId) throws StripeException {

        Map<String, Object> retrieveParams = new HashMap<>();
        List<String> expandList = new ArrayList<>();
        expandList.add("sources");
        retrieveParams.put("expand", expandList);

        Customer customer = Customer.retrieve(userId, retrieveParams, null);

        Map<String, Object> params = new HashMap<>();
        params.put("source", "tok_mastercard");

        Card card = (Card) customer.getSources().create(params);

        return card.toJson();
    }

    @PutMapping("/users/{userId}/card")
    @ResponseStatus(HttpStatus.OK)
    public String updateCard(@PathVariable String userId) throws StripeException {
        Customer customer = Customer.retrieve(userId);

        CustomerUpdateParams params =
                CustomerUpdateParams.builder()
                        .setSource("tok_visa")
                        .build();

        customer.update(params);

        return customer.toJson();
    }

    @PostMapping("/charge/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String chargeCustomer(@PathVariable String userId) throws StripeException {
        // Charge the Customer instead of the card:
        ChargeCreateParams chargeParams =
                ChargeCreateParams.builder()
                        .setAmount(100L)
                        .setCurrency("cad")
                        .setCustomer(userId)
                        .build();

        Charge charge = Charge.create(chargeParams);

        return charge.toJson();
    }

}
