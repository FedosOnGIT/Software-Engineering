package service

import io.ktor.util.*
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import model.entity.ClientsSubscriptions
import model.entity.Entrances
import module.dto.ClientEntrances
import module.dto.DateStatistics
import module.dto.UserStatistics
import module.response.DateStatisticsResponse
import module.response.UserStatisticsResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.SqlType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Collectors.groupingBy

class ReportStatistics(val database: Database) {
    fun dateStatistics(): DateStatisticsResponse {
        val day = Entrances.time.cast(SqlType.of<LocalDate>()!!).aliased("day")
        val visits = count(Entrances.enter).aliased("visits")
        val statistics = database.from(Entrances)
            .select(day, visits)
            .where(Entrances.enter eq true)
            .groupBy(day)
            .map {row ->
                DateStatistics(row[day]!!.toKotlinLocalDate(), row[visits]!!)
            }
        return DateStatisticsResponse(statistics)
    }

    fun avgStatistics() : UserStatisticsResponse {
        val entrances = database.from(ClientsSubscriptions)
            .innerJoin(Entrances, on = ClientsSubscriptions.subscriptionId eq Entrances.subscriptionId)
            .select(ClientsSubscriptions.clientId, Entrances.enter, Entrances.time)
            .where(Entrances.enter.isNotNull())
            .orderBy(Entrances.time.asc())
            .map {row ->
                ClientEntrances(
                    clientId = row[ClientsSubscriptions.clientId]!!,
                    enter = row[Entrances.enter]!!,
                    time = row[Entrances.time]!!
                )
            }
        return UserStatisticsResponse(entrances
            .stream()
            .collect(groupingBy { entrance -> entrance.clientId })
            .entries.stream()
            .map {(clientId, entrance) ->
                var all = 0L
                var visits = 0
                for (i in 0 until entrance.size - 1 step 2) {
                    all += ChronoUnit.SECONDS.between(entrance[i].time, entrance[i + 1].time)
                    visits++
                }
                UserStatistics(clientId, visits, averageTime = all / visits)
            }.toList())
    }
}