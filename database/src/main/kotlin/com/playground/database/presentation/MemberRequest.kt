package com.playground.database.presentation

class MemberRequest{
    data class CreateMember(
        val name: String,
        val email: String,
        val teamId: Long
    )

    data class UpdateTeam(
        val name: String,
        val teamId: Long
    )
}
