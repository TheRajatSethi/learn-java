package rs.customer.service.coreCustomer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateSubStatusesDto {
    String contextField;
    String field;
    String value;
    String message;
}
