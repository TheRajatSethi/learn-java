package rs.customer.service.coreCustomer.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateSubStatusesDto {
    String contextField;
    String field;
    String value;
    String message;
}
