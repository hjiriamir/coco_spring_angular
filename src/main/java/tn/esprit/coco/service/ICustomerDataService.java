package tn.esprit.coco.service;

import tn.esprit.coco.entity.CustomerData;

import java.util.List;
import java.util.Optional;

public interface ICustomerDataService {
    public CustomerData saveCustomerData(CustomerData customerData);

    public Optional<CustomerData> getCustomerDataById(String id) ;

    public List<CustomerData> getAllCustomerDatas();

}