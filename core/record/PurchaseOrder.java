package record;

import java.io.Serializable;
import java.time.LocalDate;

public record PurchaseOrder(LocalDate date, String description) implements Serializable { }