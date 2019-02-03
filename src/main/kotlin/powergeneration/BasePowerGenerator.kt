package powergeneration

import java.util.*
import java.util.concurrent.TimeUnit

open class BasePowerGenerator(protected var previousDateTime: Date) {
    protected fun timeDifferenceInHours(startDateTime: Date, endDateTime: Date): Double {
        val differenceInMilliseconds = endDateTime.time - startDateTime.time
        return TimeUnit.HOURS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS).toDouble()
    }

    protected fun updatePreviousDateTime(newDateTime: Date) {
        previousDateTime = newDateTime
    }
}