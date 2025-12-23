package rs.customer.service.coreCustomer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.customer.rda.CustomerType;
import rs.customer.rda.Tenancy;
import rs.customer.service.coreCustomer.service.dto.CustomerDto;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
public class CoreService {

    @Autowired
    CoreRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Create a customer.
     */
    public int createCustomer(CustomerDto customer){
        validateInboundCustomerCreatePayload(customer);

        Customer c = new Customer(null,
                CustomerType.valueOf(customer.getCustomer_type()),
                Tenancy.valueOf(customer.getTenancy())
        );
        try{
            return repository.insertCustomer(c);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get customer by id.
     */
    public Optional<CustomerDto> getCustomerById(int id){
        return Optional.empty();
    }


    private void validateInboundCustomerCreatePayload(CustomerDto customerDto){

    }

    private void createPerson(){

    }

    private void createCompany(){

    }

}
