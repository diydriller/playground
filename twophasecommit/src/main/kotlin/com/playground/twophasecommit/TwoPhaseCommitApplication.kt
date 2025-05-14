package com.playground.twophasecommit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwoPhaseCommitApplication

fun main(args: Array<String>) {
    runApplication<TwoPhaseCommitApplication>(*args)
}