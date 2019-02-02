package powergeneration

import java.util.*
import java.util.concurrent.TimeUnit

class ConstantSolarPanel(
    override var previousDateTime: Date,
    val hourlyPowerGenerationRate: Double
) : PowerGenerator {

    override fun powerGeneratedForDateTime(currentDateTime: Date): Double {
        val hoursElapsed = timeDifferenceInHours(previousDateTime, currentDateTime)
        val powerGenerated = hoursElapsed * hourlyPowerGenerationRate
        updatePreviousDateTime(currentDateTime)
        return powerGenerated
    }

    private fun timeDifferenceInHours(startDateTime: Date, endDateTime: Date): Double {
        val differenceInMilliseconds = endDateTime.time - startDateTime.time
        return TimeUnit.HOURS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS).toDouble()
    }

    private fun updatePreviousDateTime(newDateTime: Date) {
        previousDateTime = newDateTime
    }
}