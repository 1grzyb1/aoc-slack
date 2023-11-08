package ovh.snet.grzybek.aocslack

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ReceivedStarsService(
    private val leaderboardClient: LeaderboardClient,
    private val slackNotifier: SlackNotifier
) {

    private var leaderBoard: LeaderBoard = leaderboardClient.getLeaderBoard()


    @Scheduled(cron = "\${aoc.slack.stars.cron:0 0/15 * * * ?}")

    fun notifyReceivedStarts() {
        val newLeaderBoard = leaderboardClient.getLeaderBoard()
        val newStars = leaderBoard.findNewStars(newLeaderBoard)
        notifySlack(newStars)
    }

    fun notifySlack(stars: List<LeaderBoard.Star>) {
        val text = stars.joinToString("\n") { it.getMessage() }
        slackNotifier.sendSlackMessage(text)
    }
}