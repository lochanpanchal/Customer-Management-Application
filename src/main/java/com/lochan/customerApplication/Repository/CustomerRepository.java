package com.lochan.customerApplication.Repository;

import com.lochan.customerApplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
