package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "customerdata")
public class CustomerData {
    @Id
    public String customerId;

    public String name;
    public String email;
    public String paymentMethodId;
    public  String cardNumber;
    public  String CVC2;
    @ElementCollection
    @CollectionTable(name = "customer_transactions", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "transaction_id")
    public List<String> transactions;

    @ElementCollection
    @CollectionTable(name = "customer_transaction_amounts", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "transaction_amount")
    public List<Double> transactionAmounts;


}
