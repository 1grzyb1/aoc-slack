package ovh.snet.grzybek.aocslack

import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SlackNotifier(@Value("\${aoc.slack.webhook.url}") private val webhookUrl: String) {

    private val slack: Slack = Slack.getInstance()

    fun sendSlackMessage(message: String) {
        val payload = "{\"text\":\"${message}\"}"
        slack.send(webhookUrl, payload)
    }
}