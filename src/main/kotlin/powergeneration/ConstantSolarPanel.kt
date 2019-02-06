package powergeneration

import java.util.*

class ConstantSolarPanel(
    previousDateTime: Date,
    private val hourlyPowerGenerationRate: Double
) : BasePowerGenerator(previousDateTime), PowerGenerator {

    override fun powerGeneratedForDateTime(currentDateTime: Date): Double {
        val hoursElapsed = timeDifferenceInHours(previousDateTime, currentDateTime)
        val powerGenerated = hoursElapsed * hourlyPowerGenerationRate
        previousDateTime = currentDateTime
        return powerGenerated
    }
}