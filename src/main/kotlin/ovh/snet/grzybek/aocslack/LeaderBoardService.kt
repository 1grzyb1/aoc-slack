package ovh.snet.grzybek.aocslack

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class LeaderBoardService(private val leaderboardClient: LeaderboardClient, private val slackNotifier: SlackNotifier) {

    @Scheduled(cron = "\${aoc.slack.leaderboard.cron:0 0 5 * * ?}")
    fun notifyCurrentLeaderBoard() {
        val newLeaderBoard = leaderboardClient.getLeaderBoard()
        val ranking = newLeaderBoard.getSortedMembersByLocalScore()
        val text = "*Current leaderboard:*\n\n" + ranking.mapIndexed { index, member ->
            member.getMessage(index + 1)
        }.joinToString("\n")
        slackNotifier.sendSlackMessage(text)
    }
}