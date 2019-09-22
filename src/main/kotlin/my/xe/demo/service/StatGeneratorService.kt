package my.xe.demo.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class StatGeneratorService {

    val adStats: MutableMap<String, AdStatistic> = mutableMapOf()

    private fun getAdStat(adId: String): AdStatistic {
        if (!adStats.containsKey(adId)) {
            adStats.put(adId, AdStatistic(adId))
        }
        return adStats[adId]!!
    }


    private fun trackSearched(event: AdsSearched) {
        event.adIds.forEach { adId ->
            val record = this.getAdStat(adId)
            record.searchReturns += 1
        }
    }

    private fun trackFetched(event: AdsFetched) {
        event.adIds.forEach { adId ->
            val record = this.getAdStat(adId)
            record.resultReturns += 1

            val pageString = String.format("${event.page} of ${event.totalPages}")

            if (record.resultReturnsPerPage.containsKey(pageString)) {
                record.resultReturnsPerPage[pageString] = record.resultReturnsPerPage[pageString]!! + 1
            } else {
                record.resultReturnsPerPage.put(pageString, 1)
            }
        }
    }

    private fun trackViewed(event: AdViewed) {

        val adId = event.adId
        val viewMode = event.viewMode

        val record = this.getAdStat(adId)

        if (viewMode.equals(1)) {
            record.clickedViews += 1
        } else {
            record.directViews += 1
        }
    }


    val events: ConcurrentLinkedQueue<AdEvent> = ConcurrentLinkedQueue()

    fun track(event: AdEvent) {
        events.add(event)
    }

    @Scheduled(fixedDelay = 1000)
    private fun processEvents() {
        while (!events.isEmpty()) {
            val event = events.poll()
            if (event is AdViewed) {
                trackViewed(event)
            } else if (event is AdsSearched) {
                trackSearched(event)
            } else if (event is AdsFetched) {
                trackFetched(event)
            }
        }
    }
}


//events

abstract class AdEvent()

class AdsSearched(
        val searchId: String,
        val adIds: List<String>
) : AdEvent()

class AdsFetched(
        val searchId: String,
        val adIds: List<String>,
        val page: Int = 1,
        val totalPages: Int = 1
) : AdEvent()

class AdViewed(
        val adId: String,
        val viewMode: Int
) : AdEvent()


// projections

class AdStatistic(
        val adId: String,
        var clickedViews: Int = 0,
        var directViews: Int = 0,
        var searchReturns: Int = 0,
        var resultReturns: Int = 0,
        var resultReturnsPerPage: MutableMap<String, Int> = mutableMapOf()
)