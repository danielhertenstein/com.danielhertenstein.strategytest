import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import powergeneration.ConstantSolarPanel
import powergeneration.SolarPanel
import powergeneration.TimeSeriesInterpolatingSolarPanel
import java.io.File
import java.io.FileWriter
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
        val fileWriter = FileWriter("testTimeSeries.csv")
        fileWriter.append("hour,rate\n")
        for (hour in 0..23) {
            val rate = hour / 2.0
            fileWriter.append("$hour,$rate\n")
        }
        fileWriter.flush()
        fileWriter.close()

        val timeSeriesSolarPanel = TimeSeriesInterpolatingSolarPanel(startDateTime, "testTimeSeries.csv")

        val testFile = File("testTimeSeries.csv")
        testFile.delete()

        val house = House(timeSeriesSolarPanel)

        endDateTime = startDateTime.plusHours(3L)
        val powerGenerated = house.getPowerGeneratedForDateTime(endDateTime)
        val expectedPower = 3 * 1.5 / 2.0
        assertEquals(powerGenerated, expectedPower)
    }
}