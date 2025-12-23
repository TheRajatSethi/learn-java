package rs.customer.service.coreCustomer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StatusDto {
    String id;
    long timestamp;
    int status;
    int reason;
    String message;
}
