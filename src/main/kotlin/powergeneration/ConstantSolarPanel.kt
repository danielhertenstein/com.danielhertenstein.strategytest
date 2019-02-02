package powergeneration

import java.util.*

class ConstantSolarPanel(
    override val previousDateTime: Date,
    val hourlyPowerGenerationRate: Double
) : PowerGenerator {
    override fun powerGeneratedForDateTime(currentDateTime: Date): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}