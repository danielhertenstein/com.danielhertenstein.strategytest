import powergeneration.PowerGenerator
import java.util.*

class House(val powerGenerator: PowerGenerator) {
    fun setPowerGenerator(powerGenerator: PowerGenerator) {}
    fun getPowerGeneratedForDateTime(datetime: Date): Double {
        return 0.0
    }
}