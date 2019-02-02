package powergeneration

import java.util.*

class FakeSolarPanel(override val previousDateTime: Date) : PowerGenerator {
    override fun powerGeneratedForDateTime(currentDateTime: Date): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}