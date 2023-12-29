package ru.vtb.javaCourse.Task4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@RefreshScope
@Configuration
@PropertySource("classpath:bd.properties")
public class DataBaseConfig {

    @Value("${postgres.driver}")
    private String driverClassName;
    @Value("${postgres.url}")
    private String dataBaseUrl;
    @Value("${postgres.username}")
    private String username;
    @Value("${postgres.password}")
    private String password;

//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName(driverClassName);
//        config.setJdbcUrl(dataBaseUrl);
//        config.setUsername(username);
//        config.setPassword(password);
//        HikariDataSource dataSource = new HikariDataSource(config);
//
//        return dataSource;
//    }
//
//
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(dataSource());
//        factoryBean.setPersistenceUnitName("TDPersistenceUnit");
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setShowSql(true);
//        adapter.setGenerateDdl(false);
//        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
//        factoryBean.setJpaVendorAdapter(adapter);
//        factoryBean.setPackagesToScan("ru.vtb.javaCourse.Task4.Entity");
//        Properties jpaProp = new Properties();
//        jpaProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        jpaProp.put("hibernate.query.factory_class", "org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory");
//        jpaProp.put("hibernate.hbm2ddl.auto", "update");
//        jpaProp.put("hibernate.show_sql", Boolean.parseBoolean("true"));
//        jpaProp.put("hibernate.connection.charset", "UTF-8");
//        jpaProp.put("hibernate.connection.release_mode", "auto");
//        //jpaProp.put("javax.persistence.validation.mode", "callback");
//        factoryBean.setJpaProperties(jpaProp);
//
//        Map<String, Object> jpaPropertyMap = new HashMap<>();
////        jpaPropertyMap.put("javax.persistence.validation.factory", localValidatorFactoryBean());
////        jpaPropertyMap.put("javax.persistence.validation.group.pre-persist", "com.td.model.validation.group.PrePersistGroup,javax.validation.groups.Default");
//        jpaPropertyMap.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
//        jpaPropertyMap.put("hibernate.cdi.extensions","true");
//        jpaPropertyMap.put("hibernate.implicit_naming_strategy","org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
//        jpaPropertyMap.put("hibernate.archive.scanner","org.hibernate.boot.archive.scan.internal.DisabledScanner");
//        factoryBean.setJpaPropertyMap(jpaPropertyMap);
//        return factoryBean;
//
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//
//        JpaTransactionManager transactionManager
//                = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(
//                entityManagerFactory().getObject());
//        return transactionManager;
//    }
}