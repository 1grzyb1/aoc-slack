package ovh.snet.grzybek.aocslack

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class LeaderBoardTest {

    @Test
    fun shouldReturnStarWhenFirstStarIsCompleted() {
        val prevLeaderBoard = createLeaderBoardWithLevels(mapOf())
        val newLeaderBoard = createLeaderBoardWithLevels(mapOf("1" to mapOf("1" to createLevel())))
        val stars = prevLeaderBoard.findNewStars(newLeaderBoard)
        assertThat(stars).hasSize(1)
        assertThat(stars[0].member).isEqualTo("Grzybek")
        assertThat(stars[0].day).isEqualTo(1)
        assertThat(stars[0].star).isEqualTo(1)
    }

    @Test
    fun shouldReturnStarWhenSecondStarIsCompleted() {
        val prevLeaderBoard = createLeaderBoardWithLevels(mapOf("1" to mapOf("1" to createLevel())))
        val newLeaderBoard =
            createLeaderBoardWithLevels(mapOf("1" to mapOf("1" to createLevel(), "2" to createLevel())))
        val stars = prevLeaderBoard.findNewStars(newLeaderBoard)
        assertThat(stars).hasSize(1)
        assertThat(stars[0].member).isEqualTo("Grzybek")
        assertThat(stars[0].day).isEqualTo(1)
        assertThat(stars[0].star).isEqualTo(2)
    }

    @Test
    fun shouldReturnStarWhenBothStarsAreCompleted() {
        val prevLeaderBoard = createLeaderBoardWithLevels(mapOf())
        val newLeaderBoard =
            createLeaderBoardWithLevels(mapOf("1" to mapOf("1" to createLevel(), "2" to createLevel())))
        val stars = prevLeaderBoard.findNewStars(newLeaderBoard)
        assertThat(stars).hasSize(2)
        assertThat(stars[0].member).isEqualTo("Grzybek")
        assertThat(stars[0].day).isEqualTo(1)
        assertThat(stars[0].star).isEqualTo(1)

        assertThat(stars[1].member).isEqualTo("Grzybek")
        assertThat(stars[1].day).isEqualTo(1)
        assertThat(stars[1].star).isEqualTo(2)
    }

    @Test
    fun shouldNotReturnStarWhenUserDidntExist() {
        val prevLeaderBoard = createLeaderBoard(mapOf())
        val newLeaderBoard =
            createLeaderBoardWithLevels(mapOf("1" to mapOf("1" to createLevel(), "2" to createLevel())))
        val stars = prevLeaderBoard.findNewStars(newLeaderBoard)
        assertThat(stars).hasSize(0)
    }

    fun createLeaderBoardWithLevels(completionDayLevel: Map<String, Map<String, LeaderBoard.Member.Level>>): LeaderBoard {
        return createLeaderBoard(
            mapOf(
                "default" to createMember("default", mapOf()),
                "2137" to createMember("Grzybek", completionDayLevel)
            )
        )
    }

    fun createLeaderBoard(member: Map<String, LeaderBoard.Member>): LeaderBoard {
        return LeaderBoard(
            1, "aoc", member
        )
    }

    fun createMember(
        name: String,
        completionDayLevel: Map<String, Map<String, LeaderBoard.Member.Level>>
    ): LeaderBoard.Member {
        return LeaderBoard.Member(name, 1,1, 1, 1, 1, completionDayLevel)
    }

    fun createLevel(): LeaderBoard.Member.Level {
        return LeaderBoard.Member.Level(1, 1)
    }
}