package com.playground.twophasecommit.infrastructure.repository.postgresql

import com.playground.twophasecommit.domain.entity.postgresql.TestPostgresql
import org.springframework.data.jpa.repository.JpaRepository

interface TestPostgresqlRepository : JpaRepository<TestPostgresql, Long> {
}