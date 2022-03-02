package model

import java.io.Serializable
import java.time.LocalDateTime

data class HistoryEntity(
    var index: Int,
    var date: LocalDateTime,
    var result: String
): Serializable{}
