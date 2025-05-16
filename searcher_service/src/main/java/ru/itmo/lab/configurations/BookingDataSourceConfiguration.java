package ru.itmo.lab.configurations;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "BookingDataSourceConfiguration",
        transactionManagerRef = "transactionManager",
        basePackages = {"ru.itmo.lab.repositories"}
)
public class BookingDataSourceConfiguration {

    @Value("${bookingsdb.url}")
    String dbUrl;

    @Value("${bookingsdb.username}")
    String username;

    @Value("${bookingsdb.password}")
    String password;

    public Map<String, String> jpaProperties() {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.dialect", "ru.itmo.lab.configurations.CustomPostgresqlDialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        jpaProperties.put("javax.persistence.transactionType", "JTA");
        return jpaProperties;
    }

    @Bean(name = "bookingEntityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder bookingEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties(), null
        );
    }


    @Bean(name = "BookingDataSourceConfiguration")
    public LocalContainerEntityManagerFactoryBean getPostgresEntityManager(
            @Qualifier("bookingEntityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("bookingDataSource") DataSource postgresDataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("ru.itmo.lab.models")
                .persistenceUnit("booking")
                .properties(jpaProperties())
                .jta(true)
                .build();
    }

    @Bean("bookingDataSourceProperties")
    public DataSourceProperties bookingDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("bookingDataSource")
    public DataSource bookingDataSource(@Qualifier("bookingDataSourceProperties") DataSourceProperties dataSourceProperties) {
        PGXADataSource pgxaDataSource = new PGXADataSource();
        pgxaDataSource.setUrl(dbUrl);
        pgxaDataSource.setUser(username);
        pgxaDataSource.setPassword(password);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(pgxaDataSource);
        xaDataSource.setUniqueResourceName("xa_booking");
        xaDataSource.setMaxPoolSize(30);
        return xaDataSource;
    }

}