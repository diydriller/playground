package com.playground.twophasecommit.presentation

import com.playground.twophasecommit.application.DualWriteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DualWriteController(
    private val dualWriteService: DualWriteService
) {
    @PostMapping("/test")
    fun test(): ResponseEntity<Void> {
        dualWriteService.test()
        return ResponseEntity.ok().build()
    }

}