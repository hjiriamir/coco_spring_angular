package tn.esprit.coco.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/stripe")

public class StripeController {
    @PostMapping ("/stripe/{amount}")
    public void payer(@PathVariable("amount") Long amount ) throws StripeException{
        Stripe.apiKey = "sk_test_51O4X5XCZicFmO9OlQe9jkt0X8JF0xPTV2zzNSCWDbl4uUMOPeQIjPbw2HGR9vO19nszhq47MUrxRexNtQNQTiu4900NQpdYyUm";

        ChargeCreateParams params =
                ChargeCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency("usd")
                        .setSource("tok_visa")
                        .build();

        Charge charge = Charge.create(params);
    }
}