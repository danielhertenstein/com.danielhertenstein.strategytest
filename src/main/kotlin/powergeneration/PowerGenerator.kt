package powergeneration

import java.util.*

interface PowerGenerator {
    val previousDateTime: Date
    fun powerGeneratedForDateTime(currentDateTime: Date): Double
}