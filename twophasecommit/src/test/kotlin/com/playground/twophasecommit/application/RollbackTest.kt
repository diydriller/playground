package com.playground.twophasecommit.application

import com.playground.twophasecommit.config.BaseIntegrationTest
import com.playground.twophasecommit.infrastructure.repository.mysql.TestMysqlRepository
import com.playground.twophasecommit.infrastructure.repository.postgresql.TestPostgresqlRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RollbackTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var testMysqlRepository: TestMysqlRepository

    @Autowired
    private lateinit var testPostgresqlRepository: TestPostgresqlRepository

    @Autowired
    private lateinit var dualWriteService: DualWriteService

    @Test
    fun `2pc rollback test`() {
        try {
            dualWriteService.testWithException()
        } catch (ex: Exception) {

        }
        assertThat(testMysqlRepository.findAll()).isEmpty()
        assertThat(testPostgresqlRepository.findAll()).isEmpty()
    }
}