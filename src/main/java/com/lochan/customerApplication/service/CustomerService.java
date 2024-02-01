package com.lochan.customerApplication.service;

import com.lochan.customerApplication.Repository.CustomerRepository;
import com.lochan.customerApplication.dto.CustomerFilterDTO;
import com.lochan.customerApplication.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManager entityManager;

    public ResponseEntity<Customer> createCustomer(Customer customer) {
        this.customerRepository.save(customer);
        return new ResponseEntity<Customer>(customer,HttpStatus.CREATED);
    }

    public ResponseEntity<Customer> updateCustomerById(Integer id) {
        if(customerRepository.existsById(id)){
            Optional<Customer> retrievedCustomer = this.customerRepository.findById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Page<Customer> getAllCustomer(CustomerFilterDTO customerFilterDTO, Pageable pageable) {
        List<Customer> allCustomerList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        Predicate filterPredicate = createFilterPredicate(customerFilterDTO, criteriaBuilder, root);
        if(filterPredicate != null){
            query.where(filterPredicate);
        }

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.get)
    }

    private Predicate createFilterPredicate(CustomerFilterDTO customerFilterDTO, CriteriaBuilder customerCriteriaBuilder, Root<Customer> customerRoot) {
        if(customerFilterDTO == null){
            return null;
        }

        Predicate firstNamePredicate = null;
        Predicate  lastNamePredicate = null;
        Predicate  streetPredicate = null;
        Predicate  addressPredicate = null;
        Predicate  cityPredicate = null;
        Predicate  statePredicate = null;
        Predicate  emailPredicate = null;
        Predicate  phonePredicate = null;
        Predicate finalPredicate;

        //Filtering customer based on firstName
        if(customerFilterDTO.getFirstName() != null && customerFilterDTO.getFirstName().isEmpty())){
            firstNamePredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("firstName")),
                    "%" + customerFilterDTO.getFirstName().toLowerCase() + "%"
            );
        }

        //Filtering customer based on lastName
        if(customerFilterDTO.getLastName() != null && customerFilterDTO.getLastName().isEmpty()){
            lastNamePredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("lastName")),
                    "%" + customerFilterDTO.getLastName().toLowerCase() + "%"
            );
        }

        //Filtering customer based on street
        if(customerFilterDTO.getStreet() != null && customerFilterDTO.getStreet().isEmpty()){
            streetPredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("street")),
                    "%" + customerFilterDTO.getStreet().toLowerCase() + "%"
            );
        }

        //Filtering customer based on address
        if(customerFilterDTO.getAddress() != null && customerFilterDTO.getAddress().isEmpty()){
            addressPredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("address")),
                    "%" + customerFilterDTO.getAddress().toLowerCase() + "%"
            );
        }

        //Filtering customer based on city
        if(customerFilterDTO.getCity() != null && customerFilterDTO.getCity().isEmpty()){
            cityPredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("city")),
                    "%" + customerFilterDTO.getCity().toLowerCase() + "%"
            );
        }

        //Filtering customer based on state
        if(customerFilterDTO.getState() != null && customerFilterDTO.getState().isEmpty()){
            statePredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("state")),
                    "%" + customerFilterDTO.getState().toLowerCase() + "%"
            );
        }

        //Filtering customer based on email
        if(customerFilterDTO.getEmail() != null && customerFilterDTO.getEmail().isEmpty()){
            emailPredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("email")),
                    "%" + customerFilterDTO.getEmail().toLowerCase() + "%"
            );
        }

        //Filtering customer based on phone
        if(customerFilterDTO.getPhone() != null && customerFilterDTO.getPhone().isEmpty()){
            phonePredicate = customerCriteriaBuilder.like(
                    customerCriteriaBuilder.lower(customerRoot.get("phone")),
                    "%" + customerFilterDTO.getPhone().toLowerCase() + "%"
            );
        }

        finalPredicate = customerCriteriaBuilder.and(
                isPredicateNull(firstNamePredicate, lastNamePredicate, streetPredicate,
                        addressPredicate, cityPredicate, statePredicate, emailPredicate,
                        phonePredicate
                )
        );
        return finalPredicate;
    }

    private static Predicate [] isPredicateNull(Predicate... predicates) {
        int count = 0;
        for(Predicate arg : predicates){
            if(arg != null){
                count++;
            }
        }
        Predicate [] nonNullArgs = new Predicate[count];
        int index = 0;
        for(Predicate arg : predicates){
            if(arg != null){
                nonNullArgs[index++] = arg;
            }
        }
        return nonNullArgs;
    }


    public ResponseEntity<Customer> getCustomerById(Integer id) {
        Customer retrievedCustomer = this.customerRepository.findById(id).orElse(null);
        return new ResponseEntity<>(retrievedCustomer, HttpStatus.FOUND);
    }

    public ResponseEntity<Customer> deleteCustomerById(Integer id) {
        Customer retrievedCustomer = this.customerRepository.findById(id).orElse(null);
        return new ResponseEntity<>(retrievedCustomer, HttpStatus.OK);
    }

}
