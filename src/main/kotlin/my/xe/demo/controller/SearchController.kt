package my.xe.demo.controller

import my.xe.demo.domain.Ad
import my.xe.demo.domain.SearchResult
import my.xe.demo.service.SearchService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/")
class SearchController(val searchService: SearchService) {

    @RequestMapping(value = "create", method = arrayOf(RequestMethod.GET))
    fun create(@RequestParam(value = "term") term: String) {
        searchService.create(term)
    }

    @RequestMapping(value = "fetch", method = arrayOf(RequestMethod.GET))
    fun fetch(@RequestParam(value = "page", required = false, defaultValue = "1") page: Int,
              @RequestParam(value = "pageSize", required = false, defaultValue = "20") size: Int): SearchResult {
        return searchService.getSearchPage(page = page, pageSize = size)
    }


    @RequestMapping(value = "view", method = arrayOf(RequestMethod.GET))
    fun viewAd(@RequestParam(value = "adId", required = true) adId: String,
               request: HttpServletRequest): Ad {
        var viewMode = 0

        if (request.headerNames.toList().contains("viewmode")) {
            viewMode = request.getHeader("viewmode").toInt()
        }

        return searchService.getAd(adId = adId, viewMode = viewMode)
    }
}