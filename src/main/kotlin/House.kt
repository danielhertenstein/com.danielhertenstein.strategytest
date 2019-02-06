import powergeneration.PowerGenerator

import java.time.LocalDateTime

class House(private var powerGenerator: PowerGenerator) {

    fun setPowerGenerator(powerGenerator: PowerGenerator) {
        this.powerGenerator = powerGenerator
    }

    fun getPowerGeneratedForDateTime(datetime: LocalDateTime): Double {
        return powerGenerator.powerGeneratedForDateTime(datetime)
    }
}