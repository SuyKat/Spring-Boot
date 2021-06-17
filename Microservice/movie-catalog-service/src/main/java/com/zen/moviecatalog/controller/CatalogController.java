package com.zen.moviecatalog.controller;

import com.zen.moviecatalog.model.CatalogItem;
import com.zen.moviecatalog.model.Movie;
import com.zen.moviecatalog.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    @RequestMapping("/{userId}")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        List<Rating> ratingList = Arrays.asList(
            new Rating("2342", 5),
            new Rating("2343", 7)
        );


        return ratingList.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getId(), Movie.class);
            return new CatalogItem(movie.getName(), "testing", rating.getRating());
        }).collect(Collectors.toList());

    }
}
