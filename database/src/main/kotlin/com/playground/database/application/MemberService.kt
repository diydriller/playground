package com.playground.database.application

import com.playground.database.jooq.generated.tables.Member.Companion.MEMBER
import com.playground.database.jooq.generated.tables.Team.Companion.TEAM
import com.playground.database.jooq.generated.tables.references.ACTIVITY_LOG
import com.playground.database.presentation.MemberRequest
import org.jooq.DSLContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberService(
    private val dsl: DSLContext
) {
    @Transactional
    fun createMember(request: MemberRequest.CreateMember) {
        val now = LocalDateTime.now()
        dsl.insertInto(MEMBER)
            .set(MEMBER.NAME, request.name)
            .set(MEMBER.EMAIL, request.email)
            .set(MEMBER.TEAM_ID, request.teamId)
            .set(MEMBER.CREATED_AT, now)
            .execute()

        dsl.update(TEAM)
            .set(TEAM.MEMBER_COUNT, TEAM.MEMBER_COUNT.plus(1))
            .where(TEAM.ID.eq(request.teamId))
            .execute()
    }

    @Transactional
    fun updateTeam(request: MemberRequest.UpdateTeam) {
        dsl.update(TEAM)
            .set(TEAM.NAME, request.name)
            .where(TEAM.ID.eq(request.teamId))
            .execute()
        createTeamActivityLog(request.teamId)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun createTeamActivityLog(teamId: Long){
        val message = "update team"
        dsl.insertInto(ACTIVITY_LOG)
            .set(ACTIVITY_LOG.TEAM_ID, teamId)
            .set(ACTIVITY_LOG.MESSAGE, message)
            .execute()
    }
}