package com.playground.twophasecommit.config

import com.atomikos.jdbc.AtomikosDataSourceBean
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@EnableJpaRepositories(
    basePackages = ["com.playground.twophasecommit.infrastructure.repository.postgresql"],
    entityManagerFactoryRef = "postgresEntityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@Configuration
class PostgresqlConfig {
    @Bean
    fun postgresqlDataSource(): DataSource {
        val ds = AtomikosDataSourceBean()
        ds.uniqueResourceName = "postgres"
        ds.xaDataSourceClassName = "org.postgresql.xa.PGXADataSource"
        val p = Properties()
        p.setProperty("user", "postgres")
        p.setProperty("password", "postgres")
        p.setProperty("serverName", "localhost")
        p.setProperty("portNumber", "5432")
        p.setProperty("databaseName", "test")
        ds.xaProperties = p
        return ds
    }

    @Bean
    fun postgresEntityManagerFactory(@Qualifier("postgresqlDataSource") postgresqlDataSource: DataSource?): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = postgresqlDataSource
        em.setPackagesToScan("com.playground.twophasecommit.domain.entity.postgresql")

        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
        properties.setProperty("hibernate.transaction.jta.platform", AtomikosJtaPlatform::class.java.name)
        properties.setProperty("javax.persistence.transactionType", "JTA")

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(properties)

        return em
    }
}