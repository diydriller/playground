package com.playground.database.application

import com.playground.database.config.BaseIntegrationTest
import com.playground.database.jooq.generated.tables.references.TEAM
import com.playground.database.presentation.MemberRequest
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DeadlockLoserDataAccessException
import org.springframework.dao.DuplicateKeyException
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class MemberTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var memberService: MemberService

    @Autowired
    private lateinit var dsl: DSLContext

    @Test
    fun `member를 연속으로 생성하면 deadlock이 발생한다`() {
        val taskCount = 20
        val exceptionList = Collections.synchronizedList(mutableListOf<Exception>())
        val number = AtomicInteger(1)

        val now = LocalDateTime.now()
        val team = dsl.insertInto(TEAM)
            .set(TEAM.NAME, "test")
            .set(TEAM.MEMBER_COUNT, 0)
            .set(TEAM.CREATED_AT, now)
            .returning()
            .fetchOne()

        val futureArray = Array(taskCount) {
            CompletableFuture.runAsync {
                try {
                    val id = number.get()
                    memberService.createMember(
                        MemberRequest.CreateMember(
                            "test_${id}",
                            "test_${id}@naver.com",
                            team!!.id!!
                        )
                    )
                } catch (e: Exception) {
                    exceptionList.add(e)
                } finally {
                    number.incrementAndGet()
                }
            }
        }
        CompletableFuture.allOf(*futureArray).join()

        Assertions.assertTrue(exceptionList.size > 0)
        exceptionList.forEach { ex ->
            println(ex.message)
            Assertions.assertTrue(ex is DeadlockLoserDataAccessException || ex is DuplicateKeyException)
        }
    }
}