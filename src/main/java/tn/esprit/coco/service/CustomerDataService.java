package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.CustomerData;
import tn.esprit.coco.repository.CustomerDataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDataService implements ICustomerDataService {
    private final CustomerDataRepository customerDataRepository;
    @Autowired
    public CustomerDataService(CustomerDataRepository customerDataRepository) {
        this.customerDataRepository = customerDataRepository;
    }
    public CustomerData saveCustomerData(CustomerData customerData) {
        return customerDataRepository.save(customerData);
    }

    public Optional<CustomerData> getCustomerDataById(String id) {
        return customerDataRepository.findById(id);
    }

    public List<CustomerData> getAllCustomerDatas() {
        return customerDataRepository.findAll();
    }

}
