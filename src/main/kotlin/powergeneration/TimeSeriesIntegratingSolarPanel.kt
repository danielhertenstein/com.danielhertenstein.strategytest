package powergeneration

import org.apache.commons.math3.analysis.UnivariateFunction
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator
import java.io.BufferedReader
import java.io.FileReader
import java.time.LocalDateTime

class TimeSeriesIntegratingSolarPanel (
    previousDateTime: LocalDateTime,
    timeSeriesFilePath: String
) : BasePowerGenerator(previousDateTime), PowerGenerator {
    private var integrator = SimpsonIntegrator()
    private var powerGenerationFunction: UnivariateFunction

    init {
        val (hours, rates) = getHoursAndRatesFromFile(timeSeriesFilePath)
        val interpolator = SplineInterpolator()
        powerGenerationFunction = interpolator.interpolate(hours, rates)
    }

    private fun getHoursAndRatesFromFile(timeSeriesFilePath: String): Pair<DoubleArray, DoubleArray> {
        val hoursList = mutableListOf<Double>()
        val rateList = mutableListOf<Double>()

        val fileReader = BufferedReader(FileReader(timeSeriesFilePath))
        fileReader.readLine()

        var line = fileReader.readLine()
        while (line != null) {
            val tokens = line.split(',')
            if (tokens.isNotEmpty()) {
                hoursList.add(tokens[0].toDouble())
                rateList.add(tokens[1].toDouble())
            }
            line = fileReader.readLine()
        }
        fileReader.close()

        return Pair(hoursList.toDoubleArray(), rateList.toDoubleArray())
    }

    override fun powerGeneratedForDateTime(currentDateTime: LocalDateTime): Double {
        var powerGenerated = 0.0

        val hoursDifference = timeDifferenceInHours(previousDateTime, currentDateTime)
        var startHour = previousDateTime.hour.toDouble()
        var endHour = startHour + hoursDifference
        while (endHour > 24) {
            powerGenerated += integrator.integrate(10, powerGenerationFunction, startHour, 24.0)
            val hoursForToday = 24.0 - startHour
            endHour -= hoursForToday
            startHour = 0.0
        }
        powerGenerated += integrator.integrate(10, powerGenerationFunction, startHour, endHour)

        previousDateTime = currentDateTime
        return powerGenerated
    }

}