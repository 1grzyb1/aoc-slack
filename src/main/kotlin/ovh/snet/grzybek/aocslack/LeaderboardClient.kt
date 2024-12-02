package ovh.snet.grzybek.aocslack

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

@Service
class LeaderboardClient(
    @Value("\${aoc.slack.session}")
    private val session: String,
    @Value("\${aoc.slack.year:#{T(java.time.Year).now().value}}")
    private val year: Int,
    @Value("\${aoc.slack.leaderboard-id}")
    private val leaderBoardId: Int,
    @Value("\${aoc.slack.max-retries:3}")
    private val maxRetries: Int,
    @Value("\${aoc.slack.min-backoff:5s}")
    private val minBackoff: Duration
) {

    private val logger = KotlinLogging.logger {}

    fun getLeaderBoard(): LeaderBoard {
        val client = WebClient.builder()
            .defaultHeader("Cookie", "session=${session}")
            .defaultHeader("User-Agent", "AOC-Slack-Integration (wikt.keska@gmail.com) - https://github.com/1grzyb1/aoc-slack")
            .build()

        val result: Mono<LeaderBoard> = client.get()
            .uri("https://adventofcode.com/$year/leaderboard/private/view/$leaderBoardId.json")
            .retrieve()
            .bodyToMono(LeaderBoard::class.java)
            .retryWhen(
                Retry.backoff(maxRetries.toLong(), minBackoff)
                    .doBeforeRetry { retrySignal ->
                        logger.warn { "Retry attempt: ${retrySignal.totalRetries() + 1}, cause: ${retrySignal.failure()}" }
                    }
            )

        return result.block()!!
    }
}