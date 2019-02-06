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
        val fileReader = BufferedReader(FileReader(timeSeriesFilePath))
        fileReader.readLine()
        val hoursList = mutableListOf<Double>()
        val rateList = mutableListOf<Double>()
        var line = fileReader.readLine()
        while (line != null) {
            val tokens = line.split(',')
            if (tokens.isNotEmpty()) {
                hoursList.add(tokens[0].toDouble())
                rateList.add(tokens[1].toDouble())
            }
            line = fileReader.readLine()
        }
        val interpolator = SplineInterpolator()
        powerGenerationFunction = interpolator.interpolate(hoursList.toDoubleArray(), rateList.toDoubleArray())
    }

    override fun powerGeneratedForDateTime(currentDateTime: LocalDateTime): Double {
        val hoursDifference = timeDifferenceInHours(previousDateTime, currentDateTime)
        val startHour = previousDateTime.hour.toDouble()
        val endHour = startHour + hoursDifference
        val powerGenerated = integrator.integrate(10, powerGenerationFunction, startHour, endHour)
        previousDateTime = currentDateTime
        return powerGenerated
    }

}