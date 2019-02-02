import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import powergeneration.ConstantSolarPanel
import powergeneration.SolarPanel
import java.util.*

class TestHouse {
    private val SECONDS_IN_AN_HOUR: Long = 60L
    private val MILLISECONDS_IN_A_SECOND: Long = 1000L

    @Test fun houseWithConstantSolarPanelReturnsItsConstant() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = Date(oneHourInMilliseconds)

        val hourlyPowerGenerationRate = 2.0
        val constantSolarPanel = ConstantSolarPanel(startDateTime, hourlyPowerGenerationRate)
        val house = House(constantSolarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, hourlyPowerGenerationRate)
    }

    @Test fun houseWithRealSolarPanelReadsFromSensor() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = Date(oneHourInMilliseconds)

        val solarPanel = SolarPanel(startDateTime)
        val house = House(solarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, 999.9)
    }

    @Test fun switchingFromRealSolarPanelToConstantSolarPanel() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = Date(oneHourInMilliseconds)

        val hourlyPowerGenerationRate = 2.0
        val constantSolarPanel = ConstantSolarPanel(startDateTime, hourlyPowerGenerationRate)
        val solarPanel = SolarPanel(startDateTime)
        val house = House(solarPanel)

        var powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, 999.9)

        house.setPowerGenerator(constantSolarPanel)
        powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, hourlyPowerGenerationRate)
    }
}