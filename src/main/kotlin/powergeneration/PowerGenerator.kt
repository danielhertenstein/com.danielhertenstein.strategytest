package powergeneration

import java.time.LocalDateTime

interface PowerGenerator {
    fun powerGeneratedForDateTime(currentDateTime: LocalDateTime): Double
}