package rs.pojos;

import java.time.LocalDate;

public record Experience(Integer person, LocalDate startDate, LocalDate endDate, String position, Integer entity) {
}
