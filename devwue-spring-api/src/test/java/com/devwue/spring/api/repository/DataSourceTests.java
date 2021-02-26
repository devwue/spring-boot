package com.devwue.spring.api.repository;

import com.devwue.spring.api.config.JpaConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@Import(JpaConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataSourceTests {
    @Autowired
    @Qualifier("ApiDataSource")
    DataSource dataSource;

    @Test
    public void connectionTest() throws SQLException {
        Connection connection = dataSource.getConnection();
        assertThat(connection).isNotNull();
        assertThat(connection.getMetaData().getDatabaseProductName()).isEqualTo("MySQL");
    }
}
