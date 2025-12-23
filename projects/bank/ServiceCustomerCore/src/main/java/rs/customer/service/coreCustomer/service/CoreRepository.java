package rs.customer.service.coreCustomer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rs.customer.rda.CustomerType;
import rs.customer.rda.Tenancy;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class CoreRepository {

    @Autowired
    JdbcClient jdbcClient;

    int insertCustomer(Customer customer) throws SQLException {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String stmt = """
                insert into customer (customer_type, tenancy)
                values (:customer_type, :tenancy)
                returning id;
                """;

        var re = jdbcClient.sql(stmt)
                .param("customer_type", customer.customerType().name())
                .param("tenancy", customer.tenancy().name())
                .update(keyHolder);

        Integer id = keyHolder.getKeyAs(Integer.class);

        if (id == null) {
            throw new SQLException("Error");
        }
        return id;
    }

    Person upsertPerson(Person person) {
        return person;
    }

    Company upsertCompany(Company company) {
        return company;
    }

    Address upsertAddress(Address address) {
        return address;
    }

}
