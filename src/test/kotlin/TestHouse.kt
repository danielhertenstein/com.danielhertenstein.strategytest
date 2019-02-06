import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import powergeneration.ConstantSolarPanel
import powergeneration.SolarPanel
import powergeneration.TimeSeriesIntegratingSolarPanel
import java.time.LocalDateTime

class TestHouse {

    private lateinit var startDateTime: LocalDateTime
    private lateinit var endDateTime: LocalDateTime

    @BeforeEach fun beforeEach() {
        startDateTime = LocalDateTime.of(2019, 2, 6, 0, 0)
        endDateTime = startDateTime.plusHours(1L)
    }

    @Test fun houseWithConstantSolarPanelReturnsItsConstant() {
        val hourlyPowerGenerationRate = 2.0
        val constantSolarPanel = ConstantSolarPanel(startDateTime, hourlyPowerGenerationRate)
        val house = House(constantSolarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, hourlyPowerGenerationRate)
    }

    @Test fun houseWithRealSolarPanelReadsFromSensor() {
        val solarPanel = SolarPanel(startDateTime)
        val house = House(solarPanel)

        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        assertEquals(powerGenerated, 999.9)
    }

    @Test fun switchingFromRealSolarPanelToConstantSolarPanel() {
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

    @Test fun timeSeriesSolarPanelReadsFromFile() {
        val timeSeriesFile = javaClass.getResource("testTimeSeries.csv").path
        val timeSeriesSolarPanel = TimeSeriesIntegratingSolarPanel(startDateTime, timeSeriesFile)
        val house = House(timeSeriesSolarPanel)

        endDateTime = startDateTime.plusHours(3L)
        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        val expectedPower = 3 * 1.5 / 2.0
        assertEquals(powerGenerated, expectedPower)
    }
}