import powergeneration.PowerGenerator

class House(val powerGenerator: PowerGenerator) {
    fun setPowerGenerator(powerGenerator: PowerGenerator) {}
    fun getPowerGenerated(): Double {}
}