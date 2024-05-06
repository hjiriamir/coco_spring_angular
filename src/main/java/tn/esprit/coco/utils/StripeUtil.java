package tn.esprit.coco.utils;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentListParams;
import com.stripe.param.PaymentMethodListParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tn.esprit.coco.entity.CustomerData;

import java.util.ArrayList;
import java.util.List;

@Component
public class StripeUtil {

    @Value("${stripe.apikey}")
    String stripeKey;

    public CustomerData getCustomer(String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        // Retrieve customer and payment methods
        Customer customer = Customer.retrieve(id);
        PaymentMethodCollection paymentMethods = PaymentMethod.list(
                PaymentMethodListParams.builder()
                        .setCustomer(id)
                        .setType(PaymentMethodListParams.Type.CARD)
                        .build()
        );

        // Fetch payment intents for the customer
        List<PaymentIntent> paymentIntents = fetchPaymentIntentsForCustomer(id);

        // Set customer data including transaction IDs and amounts
        return setCustomerData(customer, paymentMethods, paymentIntents);
    }

    private List<PaymentIntent> fetchPaymentIntentsForCustomer(String customerId) throws StripeException {
        List<PaymentIntent> paymentIntents = new ArrayList<>();

        PaymentIntentListParams listParams = PaymentIntentListParams.builder()
                .setCustomer(customerId)
                .build();

        PaymentIntentCollection paymentIntentCollection = PaymentIntent.list(listParams);
        paymentIntents.addAll(paymentIntentCollection.getData());

        return paymentIntents;
    }


    public CustomerData setCustomerData(Customer customer, PaymentMethodCollection paymentMethods, List<PaymentIntent> paymentIntents) {
        CustomerData customerData = new CustomerData();
        customerData.setCustomerId(customer.getId());
        customerData.setName(customer.getName());
        customerData.setEmail(customer.getEmail());

        // Set payment method ID if available
        if (paymentMethods.getData().size() > 0) {
            PaymentMethod paymentMethod = paymentMethods.getData().get(0);
            customerData.setPaymentMethodId(paymentMethod.getId());
        }

        return customerData;
    }

}
