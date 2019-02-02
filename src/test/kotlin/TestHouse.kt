import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import powergeneration.FakeSolarPanel
import powergeneration.SolarPanel
import java.util.*

class TestHouse {
    private val SECONDS_IN_AN_HOUR: Long = 60L
    private val MILLISECONDS_IN_A_SECOND: Long = 1000L

    @Test fun HouseWithFakeSolarPanelReadsFromFile() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = startDateTime + Date(oneHourInMilliseconds)

        val hourlyPowerGenerationRate = 2.0
        val fakeSolarPanel = FakeSolarPanel(startDateTime, hourlyPowerGenerationRate)
        val house = House(fakeSolarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, hourlyPowerGenerationRate)
    }

    @Test fun HouseWithRealSolarPanelReadsFromSensor() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = startDateTime + Date(oneHourInMilliseconds)

        val solarPanel = SolarPanel(startDateTime)
        val house = House(solarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, 999.9)
    }

    @Test fun SwitchingFromRealSolarPanelToFakeSolarPanel() {
        val startDateTime = Date(0L)
        val oneHourInMilliseconds = MILLISECONDS_IN_A_SECOND * SECONDS_IN_AN_HOUR
        val endDateTime = startDateTime + Date(oneHourInMilliseconds)

        val hourlyPowerGenerationRate = 2.0
        val fakeSolarPanel = FakeSolarPanel(startDateTime, hourlyPowerGenerationRate)
        val solarPanel = SolarPanel(startDateTime)
        val house = House(solarPanel)

        var powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, 999.9)

        house.setPowerGenerator(fakeSolarPanel)
        powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, hourlyPowerGenerationRate)
    }
}