package powergeneration

import java.util.*

interface PowerGenerator {
    var previousDateTime: Date
    fun powerGeneratedForDateTime(currentDateTime: Date): Double
}