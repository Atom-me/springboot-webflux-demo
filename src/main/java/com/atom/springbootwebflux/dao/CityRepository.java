package com.atom.springbootwebflux.dao;

import com.atom.springbootwebflux.domain.City;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Atom
 */
@Repository
public interface CityRepository extends ReactiveMongoRepository<City,Long> {
    Mono<City> findByCityName(String cityName);


}
