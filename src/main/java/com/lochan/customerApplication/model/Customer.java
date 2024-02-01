package com.lochan.customerApplication.model;

import jakarta.persistence.*;
import lombok.*;

//
//"first_name": "Jane",
//        "last_name": "Doe",
//        "street": "Elvnu Street",
//        "address": "H no 2 ",
//        "city": "Delhi",
//        "state": "Delhi",
//        "email": "sam@gmail.com",
//        "phone": "12345678

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "customer_table")
public class Customer {

    @Id
    private Integer id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;

}
