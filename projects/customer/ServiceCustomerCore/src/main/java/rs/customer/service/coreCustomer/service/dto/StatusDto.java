package rs.customer.service.coreCustomer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusDto {
    String id;
    long timestamp;
    int status;
    int reason;
    String message;
}
