package ovh.snet.grzybek.aocslack

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class LeaderBoardService(
    private val leaderboardClient: LeaderboardClient, private val slackNotifier: SlackNotifier,
    @Value("\${aoc.slack.only-active-users:false}")
    private val onlyActiveUsers: Boolean,
    @Value("\${aoc.slack.use-stars:false}")
    private val useStars: Boolean,
) {

    @Scheduled(cron = "\${aoc.slack.leaderboard.cron:0 0 5 * * ?}")
    fun notifyCurrentLeaderBoard() {
        val newLeaderBoard = leaderboardClient.getLeaderBoard()
        var ranking =
            if (useStars) newLeaderBoard.getMembersSortedByLocalStore()
            else newLeaderBoard.getMembersSortedByStars()

        if (onlyActiveUsers) {
            ranking = ranking.filter { it.localScore > 0 }
        }

        val text = "*Current leaderboard:*\n\n" + ranking.mapIndexed { index, member ->
            if (useStars) member.getLocalScoreMessage(index + 1) else member.getStarMessage(index + 1)
        }.joinToString("\n")
        slackNotifier.sendSlackMessage(text)
    }
}