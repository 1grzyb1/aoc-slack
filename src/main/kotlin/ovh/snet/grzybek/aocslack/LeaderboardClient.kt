package ovh.snet.grzybek.aocslack

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class LeaderboardClient(
    @Value("\${aoc.slack.session}")
    private val session: String,
    @Value("\${aoc.slack.year:#{T(java.time.Year).now().value}}")
    private val year: Int,
    @Value("\${aoc.slack.leaderboard-id}")
    private val leaderBoardId: Int
) {

    fun getLeaderBoard(): LeaderBoard {
        val client = WebClient.builder()
            .defaultHeader("Cookie", "session=${session}")
            .build()
        val result: Mono<LeaderBoard> = client.get()
            .uri("https://adventofcode.com/${year}/leaderboard/private/view/${leaderBoardId}.json")
            .retrieve()
            .bodyToMono(LeaderBoard::class.java)
        return result.block()!!
    }
}
