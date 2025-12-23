package rs.customer.service.coreCustomer.service;

import rs.customer.rda.AddressContext;
import rs.customer.rda.CustomerType;
import rs.customer.rda.Gender;
import rs.customer.rda.Tenancy;

import java.time.LocalDate;


record Customer(Integer id, CustomerType customerType, Tenancy tenancy) {}
record Person  (Integer id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender){}
record Company (Integer id, String registrationNumber, LocalDate registrationDate){}
record Address (Integer id, AddressContext addressContext, Integer customerId, String line1, String line2, String line3, String countryIso3, String Postal){}
