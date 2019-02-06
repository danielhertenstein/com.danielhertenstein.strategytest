package powergeneration

import java.time.LocalDateTime

class ConstantSolarPanel(
    previousDateTime: LocalDateTime,
    private val hourlyPowerGenerationRate: Double
) : BasePowerGenerator(previousDateTime), PowerGenerator {

    override fun powerGeneratedForDateTime(currentDateTime: LocalDateTime): Double {
        val hoursElapsed = timeDifferenceInHours(previousDateTime, currentDateTime)
        val powerGenerated = hoursElapsed * hourlyPowerGenerationRate
        previousDateTime = currentDateTime
        return powerGenerated
    }
}