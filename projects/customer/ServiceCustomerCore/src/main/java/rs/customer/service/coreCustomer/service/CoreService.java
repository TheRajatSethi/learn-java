package rs.customer.service.coreCustomer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
class CoreService {

    @Autowired
    ObjectMapper objectMapper;

    void createCustomer(String payload){
        CustomerDto customer = convertInboundCustomerCreatePayload(payload);
        validateInboundCustomerCreatePayload(customer);
    }

    Optional<CustomerDto> getCustomerById(int id){
        return Optional.empty();
    }

    private CustomerDto convertInboundCustomerCreatePayload(String payload){
        CustomerDto customer = null;
        try {
            customer = objectMapper.readValue(payload,CustomerDto.class);
            log.info(customer.toString());
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return customer;
    }

    private void validateInboundCustomerCreatePayload(CustomerDto customerDto){
        
    }
}
