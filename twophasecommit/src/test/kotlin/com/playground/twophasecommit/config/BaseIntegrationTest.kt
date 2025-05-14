package com.playground.twophasecommit.config

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@Testcontainers
abstract class BaseIntegrationTest {
    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.35").apply {
            withDatabaseName("testdb")
            withUsername("root")
            withPassword("rootpass")
        }

        @Container
        private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14.1")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("test")

        @JvmStatic
        @DynamicPropertySource
        fun setDatasourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.jta.log-dir") {
                "./build/atomikos-logs/test-${UUID.randomUUID()}"
            }
        }
    }
}