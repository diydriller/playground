package com.playground.twophasecommit.infrastructure.repository.mysql

import com.playground.twophasecommit.domain.entity.mysql.TestMysql
import org.springframework.data.jpa.repository.JpaRepository

interface TestMysqlRepository : JpaRepository<TestMysql, Long> {
}