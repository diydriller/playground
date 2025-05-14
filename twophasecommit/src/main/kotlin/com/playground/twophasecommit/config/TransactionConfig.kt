package com.playground.twophasecommit.config

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.jta.JtaTransactionManager

@Configuration
class TransactionConfig {
    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val userTransactionImp = UserTransactionImp()
        userTransactionImp.setTransactionTimeout(300)

        val userTransactionManager = UserTransactionManager()
        userTransactionManager.init()

        return JtaTransactionManager(userTransactionImp, userTransactionManager)
    }
}