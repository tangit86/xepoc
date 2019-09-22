package my.xe.demo.service

import my.xe.demo.domain.Ad
import my.xe.demo.domain.Search
import my.xe.demo.domain.SearchResult
import my.xe.demo.repo.AdRepository
import org.springframework.stereotype.Service

@Service
class SearchService(
        val adRepository: AdRepository,
        val statGeneratorService: StatGeneratorService) {

    fun create(term: String) {
        val search = adRepository.create(term)
        System.out.println(String.format("Search created ${search.searchId}:  ${search.results.size.toString()} results"))
        statGeneratorService.track(AdsSearched(searchId = search.searchId,
                adIds = search.results.map { it.id }))
    }

    fun getSearchPage(page: Int, pageSize: Int): SearchResult {
        val search = adRepository.getSearch()
        val result = searchToResultMap(search, page, pageSize)
        if (result.payload.pageElements > 0) {
            System.out.println("Fetched from ${search.searchId} : page: [${result.payload.page}/${result.payload.totalPages}] , elements: [${result.payload.pageElements}/${result.payload.totalElements}] ")
            statGeneratorService.track(AdsFetched(searchId = search.searchId,
                    adIds = result.payload.results.map { it.id },
                    page = page,
                    totalPages = result.payload.totalPages))
        }
        return result
    }


    fun getAd(adId: String, viewMode: Int): Ad {
        var ad = adRepository.getAd(adId)
        System.out.println("Ad " + ad.id + " was viewed in " + viewModes[viewMode] + " mode")
        statGeneratorService.track(AdViewed(ad.id, viewMode))
        return ad
    }

}


fun searchToResultMap(search: Search, page: Int, pageSize: Int): SearchResult {
    val ads = search.results

    val payload = ads.getPage(page, pageSize)

    return SearchResult(
            searchId = search.searchId,
            payload = payload
    )
}