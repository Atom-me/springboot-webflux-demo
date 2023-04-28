package com.atom.springbootwebflux.controller;

import com.atom.springbootwebflux.domain.City;
import com.atom.springbootwebflux.handler.CityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Boot WebFlux 开发中，不需要配置。Spring Boot WebFlux 可以使用自动配置加注解驱动的模式来进行开发。
 *
 * @author Atom
 */
@Controller
@RequestMapping("/city")
public class CityWebFluxController {

    private static final String CITY_LIST_PATH_NAME = "cityList";
    private static final String CITY_PATH_NAME = "city";


    @Autowired
    private CityHandler cityHandler;

    @GetMapping("/{id}")
    @ResponseBody
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return cityHandler.findCityById(id);
    }

    /**
     * curl --location 'localhost:8080/city/'
     *
     * @return
     */
    @GetMapping
    @ResponseBody
    public Flux<City> findAllCity() {
        return cityHandler.findAllCity();
    }


    /**
     * <p>
     * curl --location 'localhost:8080/city/' \
     * --header 'Content-Type: application/json' \
     * --data '{
     * "id":2,
     * "provinceId":2,
     * "cityName":"tianjin",
     * "description":"天津市"
     * }'
     * </p>
     *
     * @param city
     * @return
     */
    @PostMapping()
    @ResponseBody
    public Mono<City> saveCity(@RequestBody City city) {
        return cityHandler.save(city);
    }


    /**
     * <p>
     * curl --location --request PUT 'localhost:8080/city/' \
     * --header 'Content-Type: application/json' \
     * --data '{
     * "id":2,
     * "provinceId":2,
     * "cityName":"tianjin",
     * "description":"天津市哈哈哈哈"
     * }'
     * </p>
     *
     * @param city
     * @return
     */
    @PutMapping
    @ResponseBody
    public Mono<City> modifyCity(@RequestBody City city) {
        return cityHandler.modifyCity(city);
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return cityHandler.deleteCity(id);
    }


    /**
     * curl --location 'http://localhost:8080/city/getByName?cityName=beijing'
     *
     * @param model
     * @param cityName
     * @return
     */
    @GetMapping("/getByName")
    public String getByCityName(final Model model,
                                @RequestParam("cityName") String cityName) {
        final Mono<City> city = cityHandler.getByCityName(cityName);
        model.addAttribute("city", city);
        return CITY_PATH_NAME;
    }


    @GetMapping("/hello")
    public Mono<String> hello(final Model model) {
        model.addAttribute("name", "atom");
        model.addAttribute("city", "北京是");

        String path = "hello";
        return Mono.create(monoSink -> monoSink.success(path));
    }


    @GetMapping("/page/list")
    public String listPage(final Model model) {
        final Flux<City> cityFluxList = cityHandler.findAllCity();
        model.addAttribute("cityList", cityFluxList);
        return CITY_LIST_PATH_NAME;
    }


}
