package my.xe.demo.domain

import java.util.*

class Ad {
    var id: String
        protected set
    var text: String
        protected set
    var customerId: Int = 0
        protected set
    var createdAt: Date
        protected set

    init {
        id = UUID.randomUUID().toString()

        val rnd = Random()

        val rndWord = Random()

        text = "This is the ad text " + RANDOM_WORDS.get(rnd.nextInt(RANDOM_WORDS.size))

        customerId = Math.abs(rnd.nextInt(500))
        createdAt = Calendar.getInstance().time
    }

}

val RANDOM_WORDS = listOf<String>("BONG", "FOO", "ASDA", "ASDA-FOO", "BEE")