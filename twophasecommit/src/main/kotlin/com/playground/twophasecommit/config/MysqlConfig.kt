package com.playground.twophasecommit.config

import com.atomikos.jdbc.AtomikosDataSourceBean
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource


@EnableJpaRepositories(
    basePackages = ["com.playground.twophasecommit.infrastructure.repository.mysql"],
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@Configuration
class MysqlConfig {
    @Bean
    fun mysqlDataSource(): DataSource {
        val ds = AtomikosDataSourceBean()
        ds.uniqueResourceName = "mysql"
        ds.xaDataSourceClassName = "com.mysql.cj.jdbc.MysqlXADataSource"
        val p = Properties()
        p.setProperty("user", "root")
        p.setProperty("password", "mysql_source")
        p.setProperty("serverName", "localhost")
        p.setProperty("portNumber", "3300")
        p.setProperty("databaseName", "test")
        ds.xaProperties = p
        return ds
    }

    @Bean
    fun mysqlEntityManagerFactory(@Qualifier("mysqlDataSource") mysqlDataSource: DataSource?): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = mysqlDataSource
        em.setPackagesToScan("com.playground.twophasecommit.domain.entity.mysql")

        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
        properties.setProperty("hibernate.transaction.jta.platform", AtomikosJtaPlatform::class.java.name)
        properties.setProperty("javax.persistence.transactionType", "JTA")

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(properties)

        return em
    }
}