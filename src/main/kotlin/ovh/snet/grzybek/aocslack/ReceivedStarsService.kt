package ovh.snet.grzybek.aocslack

import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ReceivedStarsService(
    private val leaderboardClient: LeaderboardClient,
    @Value("\${aoc.slack.webhook.url}") private val webhookUrl: String
) {

    private var leaderBoard: LeaderBoard = leaderboardClient.getLeaderBoard()
    private val slack: Slack = Slack.getInstance()

    @Scheduled(cron = "\${aoc.slack.stars.cron:0 0/15 * * * ?}")

    fun notifyReceivedStarts() {
        val newLeaderBoard = leaderboardClient.getLeaderBoard()
        val newStars = leaderBoard.findNewStars(newLeaderBoard)
        notifySlack(newStars)
    }

    fun notifySlack(stars: List<LeaderBoard.Star>) {
        val text = stars.joinToString("\n") { it.getMessage() }
        val payload = "{\"text\":\"${text}\"}"
        slack.send(webhookUrl, payload)
    }
}