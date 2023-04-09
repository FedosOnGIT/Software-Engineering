package model.event

import java.time.LocalDateTime

data class TurnstileEvent(val subscriptionId : Int, val enter: Boolean, val time: LocalDateTime)
