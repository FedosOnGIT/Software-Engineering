package module.dto

import java.time.LocalDateTime

data class ClientEntrances(val clientId : Int, val enter: Boolean, val time: LocalDateTime)
