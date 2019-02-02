import powergeneration.PowerGenerator
import java.util.*

class House(private var powerGenerator: PowerGenerator) {

    fun setPowerGenerator(powerGenerator: PowerGenerator) {
        this.powerGenerator = powerGenerator
    }

    fun getPowerGeneratedForDateTime(datetime: Date): Double {
        return powerGenerator.powerGeneratedForDateTime(datetime)
    }
}