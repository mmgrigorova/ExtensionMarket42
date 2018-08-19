package com.antman.extensionmarket42.configuration;

import com.antman.extensionmarket42.extensions.models.Extension;
import com.antman.extensionmarket42.extensions.models.ExtensionTag;
import com.antman.extensionmarket42.extensions.models.Screenshot;
import com.antman.extensionmarket42.extensions.models.Tag;
import com.antman.extensionmarket42.models.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public SessionFactory createSessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Extension.class)
                .addAnnotatedClass(ExtensionTag.class)
                .addAnnotatedClass(Screenshot.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserProfile.class)
                .addAnnotatedClass(UserRole.class)
                .buildSessionFactory();
    }

    @Bean
    public DataSource securityDataSource(){
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        try{
            securityDataSource.setDriverClass(environment.getProperty("database.driver"));
        } catch (PropertyVetoException e){
            throw new RuntimeException(e);
        }

        securityDataSource.setJdbcUrl(environment.getProperty("database.url"));
        securityDataSource.setUser(environment.getProperty("database.username"));
        securityDataSource.setPassword(environment.getProperty("database.password"));

        securityDataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("connection.initialPoolSize")));
        securityDataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("connection.maxPoolSize")));
        securityDataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("connection.maxPoolSize")));
        securityDataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("connection.maxIdleTime")));

        return securityDataSource;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(securityDataSource());
        return jdbcUserDetailsManager;
    }

    @Primary
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(securityDataSource());
        lcemfb.setPackagesToScan("com.antman.extensionmarket42.models",
                "com.antman.extensionmarket42.extensions.models");
        lcemfb.setPersistenceProvider(new HibernatePersistenceProvider());
        return lcemfb;
    }

}
