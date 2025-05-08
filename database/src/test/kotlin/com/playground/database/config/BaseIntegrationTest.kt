package com.playground.database.config

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class BaseIntegrationTest {
    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.35").apply {
            withDatabaseName("testdb")
            withUsername("root")
            withPassword("rootpass")
        }

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("storage.datasource.url") { mysqlContainer.jdbcUrl }
            registry.add("storage.datasource.username") { mysqlContainer.username }
            registry.add("storage.datasource.password") { mysqlContainer.password }
        }
    }
}