package com.playground.database.presentation

import com.playground.database.application.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/member")
    fun createMember(@RequestBody request: MemberRequest.CreateMember): ResponseEntity<Void> {
        memberService.createMember(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/team")
    fun updateTeam(@RequestBody request: MemberRequest.UpdateTeam): ResponseEntity<Void> {
        memberService.updateTeam(request)
        return ResponseEntity.ok().build()
    }
}