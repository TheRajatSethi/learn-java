package com.validation.Validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {
    @CustomListValidation(runFunc = MyValidator.class)
    @JsonProperty("Country")
    String country;

    @CustomListValidation(runFunc = MyValidator.class)
    @JsonProperty("PostalCode")
    String postalCode;

    @Override
    public String toString() {
        return this.country + " " + this.postalCode;
    }
}
