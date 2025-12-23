package com.rs.stocks.users;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

class D {
    private static Jdbi jdbi;

    static Jdbi getJdbi(){
        return jdbi;
    }

    static void initializeDependencies(){
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.addDataSourceProperty("serverName", "localhost"); // or your host
        config.addDataSourceProperty("portNumber", "5432"); // default PostgreSQL port
        config.addDataSourceProperty("databaseName", "stocks");
        config.addDataSourceProperty("user", "postgres");
        config.addDataSourceProperty("password", "postgres");
        config.addDataSourceProperty("tcpKeepAlive", "true");
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(5);

        HikariDataSource ds = new HikariDataSource(config);
        jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());

    }
}
