package ovh.snet.grzybek.aocslack

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
data class LeaderBoard(val ownerId: Int, val event: String, val members: Map<String, Member>) {
    fun findNewStars(newLeaderBoard: LeaderBoard): List<Star> {
        val newStars = mutableListOf<Star>()

        for ((memberId, newMember) in newLeaderBoard.members) {
            val oldMember = this.members[memberId] ?: continue
            newStars.addAll(addStars(newMember, oldMember))
        }

        return newStars
    }

    private fun addStars(newMember: Member, oldMember: Member): List<Star> {
        val newStars = mutableListOf<Star>()
        newMember.completionDayLevel.forEach { (day, starsMap) ->
            starsMap.forEach { (star, _) ->
                val oldLevel = oldMember.completionDayLevel[day]?.get(star)
                if (oldLevel == null) {
                    newStars.add(Star(oldMember.getMemberName(), day.toInt(), star.toInt()))
                }
            }
        }
        return newStars
    }

    fun getSortedMembersByLocalScore(): List<Member> {
        return members.values.sortedByDescending { it.localScore }
    }

    data class Member(
        val name: String?,
        val id: Int,
        val stars: Int,
        @JsonProperty("local_score")
        val localScore: Int,
        val globalScore: Int,
        @JsonProperty("last_star_ts")
        val lastStarTs: Long,
        @JsonProperty("completion_day_level")
        val completionDayLevel: Map<String, Map<String, Level>> = mapOf()
    ) {

        @JsonIgnoreProperties
        fun getMemberName(): String {
            if (name != null) {
                return name
            }
            return "Anonymous user #${id}"
        }

        fun getMessage(place: Int): String {
            return "${place}) *${getMemberName()}* ${localScore}\n"
        }


        data class Level(val getStarTs: Long, val starIndex: Long)
    }

    data class Star(val member: String, val day: Int, val star: Int) {
        fun getMessage(): String {
            return "*${member}* received ${":star:".repeat(star)} for solving ${day} challenge :tada:\n"
        }
    }
}

