package rs.customer.service.coreCustomer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {
    String firstName;
    String lastName;
    String dateOfBirth;
    String gender;
    AddressDto address;
}
