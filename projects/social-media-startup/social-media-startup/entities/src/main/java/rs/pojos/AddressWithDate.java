package rs.pojos;

import java.time.LocalDate;

public record AddressWithDate(LocalDate fromDate, LocalDate toDate, Address address) {
}
