package ovh.snet.grzybek.aocslack

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ReceivedStarsService(private val leaderboardClient: LeaderboardClient) {

    private var leaderBoard: LeaderBoard = leaderboardClient.getLeaderBoard()

    @Scheduled(fixedDelay = 5000)
    fun notifyReceivedStarts() {
        val newLeaderBoard = leaderboardClient.getLeaderBoard()
        val newStars = leaderBoard.findNewStars(newLeaderBoard)
    }
}