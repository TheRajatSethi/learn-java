package com.rs;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

@ApplicationScoped
public class JdbiProvider {
    @Inject
    AgroalDataSource ds;

    @Produces
    public Jdbi jdbi(){
        return Jdbi.create(ds).installPlugin(new SQLitePlugin());
    }

}