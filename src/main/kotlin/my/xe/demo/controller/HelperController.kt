package my.xe.demo.controller

import my.xe.demo.repo.AdRepository
import my.xe.demo.service.StatGeneratorService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/helper")
class HelperController(val statService:StatGeneratorService, val adRepository: AdRepository){

    @RequestMapping("/stats")
    fun getStats():Any{
        return statService.adStats
    }

    @RequestMapping("/data")
    fun getData():Any{
        return adRepository.getSearch()
    }
}