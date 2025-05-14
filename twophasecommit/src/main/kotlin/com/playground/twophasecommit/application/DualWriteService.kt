package com.playground.twophasecommit.application

import com.playground.twophasecommit.domain.entity.mysql.TestMysql
import com.playground.twophasecommit.domain.entity.postgresql.TestPostgresql
import com.playground.twophasecommit.infrastructure.repository.mysql.TestMysqlRepository
import com.playground.twophasecommit.infrastructure.repository.postgresql.TestPostgresqlRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DualWriteService(
    private val testMysqlRepository: TestMysqlRepository,
    private val testPostgresqlRepository: TestPostgresqlRepository
) {
    @Transactional
    fun test() {
        val testMysql = TestMysql(
            name = "mysql"
        )
        testMysqlRepository.save(testMysql)

        val testPostgresql = TestPostgresql(
            name = "postgresql"
        )
        testPostgresqlRepository.save(testPostgresql)
    }

    @Transactional
    fun testWithException() {
        val testMysql = TestMysql(
            name = "mysql"
        )
        testMysqlRepository.save(testMysql)

        val testPostgresql = TestPostgresql(
            name = "postgresql"
        )
        testPostgresqlRepository.save(testPostgresql)

        throw RuntimeException("Rollback Exception")
    }
}