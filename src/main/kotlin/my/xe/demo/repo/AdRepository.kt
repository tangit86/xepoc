package my.xe.demo.repo

import my.xe.demo.domain.Ad
import my.xe.demo.domain.Search
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class AdRepository {

    private val ads: MutableMap<String, Ad> = mutableMapOf()

    private var search: Search = Search("",listOf())

    init {
        val rnd = Random()
        val numberOfResults = Math.max(50, rnd.nextInt(500))
        for (i in 0 until numberOfResults) {
            val ad = Ad()
            ads.put(ad.id, ad)
        }
    }

    fun create(term:String): Search {
        search = Search(results = ads.values.filter {it.text.contains(term) })
        return search
    }

    fun getSearch(): Search {
        return search
    }


    fun getAd(adId: String): Ad {
        return ads.get(adId)!!
    }
}