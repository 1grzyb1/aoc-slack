package ovh.snet.grzybek.aocslack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AocSlackApplication

fun main(args: Array<String>) {
    runApplication<AocSlackApplication>(*args)
}
