package my.xe.demo.service

import kotlin.math.roundToInt

class Page<T>(
        val page: Int,
        val pageElements:Int,
        val totalPages: Int,
        val pageSize: Int,
        val totalElements: Int,
        val results: List<T>
)

fun <T> List<T>.getPage(page: Int, pageSize: Int): Page<T> {

    var totalElements = this.size

    var correctPageSize = 20
    if(pageSize in 1..50){
        correctPageSize = pageSize
    }

    var totalPages = Math.ceil(totalElements*1.0/correctPageSize).roundToInt()

    var correctPage = 1
    if(page in 1..totalPages){
        correctPage = page
    }

    val start = (correctPage - 1) * correctPageSize
    var end = correctPage * correctPageSize - 1
    if (end >= totalElements) {
        end = totalElements -1
    }

    val result = this.slice(start..end)

    return Page(
            page = correctPage,
            pageElements = result.size,
            totalPages = totalPages,
            pageSize = correctPageSize,
            totalElements = totalElements,
            results = result
    )
}

val viewModes = mapOf<Int,String>(
        0 to "DIRECT",
        1 to "CLICKED"
)