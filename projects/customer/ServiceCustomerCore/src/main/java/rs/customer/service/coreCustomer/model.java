package rs.customer.service.coreCustomer.service;


import org.springframework.data.annotation.Id;
import rs.customer.rda.CustomerType;
import rs.customer.rda.Gender;
import rs.customer.rda.Tenancy;

import java.time.LocalDate;

public record Customer (@Id Integer id, CustomerType customerType, Tenancy tenancy){};

record Person (@Id Integer id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender){};
