package rs.customer.service.coreCustomer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDto {
    String name;
    String registrationDate;
    String registrationNumber;
}
