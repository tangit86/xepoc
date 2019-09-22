package my.xe.demo.domain

import my.xe.demo.service.Page

class SearchResult(
        val searchId:String,
        val payload: Page<Ad>
)