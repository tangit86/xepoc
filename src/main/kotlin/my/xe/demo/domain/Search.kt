package my.xe.demo.domain

import java.util.*

class Search(
        val searchId:String = UUID.randomUUID().toString(),
        val results:List<Ad>
)