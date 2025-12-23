package rs.customer.service.coreCustomer.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    String customer_type;
    String tenancy;
    PersonDto person;
    CompanyDto company;
}
