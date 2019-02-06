package powergeneration

import java.util.*

class SolarPanel(previousDateTime: Date) : BasePowerGenerator(previousDateTime), PowerGenerator{
    override fun powerGeneratedForDateTime(currentDateTime: Date): Double {
        // TODO: Ideally this class reads data from a sensor
        return 999.9
    }
}