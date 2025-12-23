package rs.customer.service.coreCustomer.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


/**
 * DTO's should contain only those field types which are valid JSON. E.g. String and Integers.
 * There can be DTO's which can contain {@link java.time.LocalDate LocalDate } instead of String for <code>YYYY-MM-DD</code>
 * but in that case the parsing error will happen with Jackson which makes it difficult to get all error's in 1 go.
 * Also, if you use the DTO as input to the controller instead of String, then the flow goes to the
 * ControllerAdvice.
 * <br><br>
 * Now the validation logic is spread between, some annotations (hibernate/javax)
 * on DTO and some custom logic once the pojo is constructed. Can be done but now it's all over the place.
 * <br><br>
 * <b>Alternative Approach</b> - Get input as string, parse to DTO which just has String, Integers, Boolean, Float (As per Json spec)
 * Trigger parsing manually and validate for business logic too e.g. whether the data is correct makes sense
 * and not just the format. Now all validation is at 1 place.
 *
 */



public class ResponseDto<T> extends ResponseEntity<T> {
    @Setter
    StatusDto status;
    @Setter
    CreateSubStatusesDto createSubstatuses;
    @Setter
    T response;

    public ResponseDto(HttpStatusCode status) {
        super(status);
    }
}

