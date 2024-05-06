package tn.esprit.coco.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.CustomerData;
import tn.esprit.coco.service.CustomerDataService;
import tn.esprit.coco.utils.StripeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE, RequestMethod.GET, RequestMethod.PATCH})
@RequestMapping("/apistripe")
public class StripePaymentControllerAPI {

    private final CustomerDataService customerDataService;

    @Autowired
    public StripePaymentControllerAPI(CustomerDataService customerDataService) {
        this.customerDataService = customerDataService;
    }
    @Value("${stripe.apikey}")
    String stripekey;

    @Autowired
    StripeUtil stripeUtil;

    // Create a customer with a hardcoded payment method ID (for testing purposes)
    @PostMapping("createcustomer")
    public CustomerData createCustomer(@RequestBody CustomerData customerData) {
        Stripe.apiKey = stripekey;
        CustomerData savedcustomerdata = new CustomerData();
        try {
            // Hardcoded payment method ID for testing. Replace with actual payment method ID.
//            String paymentMethodId = "pm_card_visa"; // Replace with an actual payment method ID

            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setName(customerData.getName())
                    .setEmail(customerData.getEmail())
                    .setPaymentMethod(customerData.getPaymentMethodId())

                    .build();

            Customer customer = Customer.create(params);

            // Set the customer ID and payment method ID in the CustomerData object
            customerData.setCustomerId(customer.getId());
            customerData.setPaymentMethodId(customerData.getPaymentMethodId());
            savedcustomerdata = customerDataService.saveCustomerData(customerData);

        } catch (StripeException e) {
            // Handle specific Stripe exceptions
            // For example: Log the error, return a custom error message, etc.
            throw new RuntimeException("Failed to create customer: " + e.getMessage());
        }
        return savedcustomerdata ;
    }
    @GetMapping("getcustomerdb")
    public List<CustomerData> listCustomerDatadb() {
        try {
            List<CustomerData> customerDataList = customerDataService.getAllCustomerDatas();
            return customerDataList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customers: " + e.getMessage());
        }
    }

    // Retrieve a list of customers
    @GetMapping("getcustomerstripe")
    public List<CustomerData> listCustomerData() {
        Stripe.apiKey = stripekey;
        List<CustomerData> customerDataList = new ArrayList<>();

        CustomerListParams params = CustomerListParams.builder().setLimit(3L).build();
        try {
            CustomerCollection customers = Customer.list(params);
            for (Customer customer : customers.getData()) {
                CustomerData customerData = new CustomerData();
                customerData.setCustomerId(customer.getId());
                customerData.setEmail(customer.getEmail());
                customerData.setName(customer.getName());
                customerDataList.add(customerData);
            }
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve customers: " + e.getMessage());
        }
        return customerDataList;
    }

    // Retrieve a specific customer by ID
    @GetMapping("getCustomer/{id}")
    public CustomerData getCustomer(@PathVariable("id") String id) {
        try {
            CustomerData customerData = stripeUtil.getCustomer(id);
            return customerData;
        } catch (StripeException e) {
            throw new RuntimeException("Failed to retrieve customer: " + e.getMessage());
        }
    }


    // Make a payment for a specific customer
//    @PostMapping("makepayment")
//    public String makePayment(@RequestBody Map<String, Object> requestParams) {
//        String customerId = (String) requestParams.get("customerId");
//        int amount = (int) requestParams.get("amount");
//        Stripe.apiKey = stripekey;
//        try {
//            Map<String, Object> params = new HashMap<>();
//            params.put("amount", amount);
//            params.put("currency", "usd");
//            params.put("customer", customerId);
//            PaymentIntent.create(params);
//            return "Payment successful";
//        } catch (StripeException e) {
//            throw new RuntimeException("Payment failed: " + e.getMessage());
//        }
//    }
    @PostMapping("makepayment")
    public ResponseEntity<Map<String, Object>> makePayment(@RequestBody Map<String, Object> requestParams) {
        String customerId = (String) requestParams.get("customerId");
        int amount = (int) requestParams.get("amount");
        Stripe.apiKey = stripekey;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 60);
            params.put("currency", "usd");
            params.put("customer", customerId);
            PaymentIntent.create(params);
            // Create a success response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", true);
            return ResponseEntity.ok(responseBody);
        } catch (StripeException e) {
            // Create an error response
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
