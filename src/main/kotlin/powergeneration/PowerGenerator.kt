package powergeneration

import java.util.*

interface PowerGenerator {
    fun powerGeneratedForDateTime(currentDateTime: Date): Double
}