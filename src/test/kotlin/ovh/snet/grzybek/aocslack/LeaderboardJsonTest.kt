package ovh.snet.grzybek.aocslack

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class LeaderboardJsonTest {

    @Test
    fun deserializeLeaderboard() {
        val jsonPath = Paths.get("src/test/resources/leaderboard.json")
        val jsonString = String(Files.readAllBytes(jsonPath))
        val objectMapper = jacksonObjectMapper()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val leaderboard = objectMapper.readValue<LeaderBoard>(jsonString)
        assertThat(leaderboard.members["3527679"]!!.getMemberName()).isEqualTo("Anonymous user #3527679")
        assertThat(leaderboard.members["1496839"]!!.getMemberName()).isEqualTo("Grzybek")
    }
}