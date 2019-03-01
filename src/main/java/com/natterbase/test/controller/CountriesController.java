package com.natterbase.test.controller;

import com.natterbase.test.model.Country;
import com.natterbase.test.repository.CountriesRepository;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountriesController  {

    @Autowired
    CountriesRepository countriesRepository;
    private Logger logger = LoggerFactory.getLogger(CountriesController.class);

    /**
     *
     * @param country the country object to be saved
     * @param Authorization This request requires Authorization header
     * @return the saved country object
     */
    @PostMapping("/")
    public Country saveCountry(@RequestBody Country country, @RequestHeader String Authorization) {
        return countriesRepository.save(country);
    }

    @ApiOperation(value = "Fetch a country")
    @GetMapping("/")
    public ResponseEntity<List<Country>> getCountries(@RequestHeader String Authorization) {
        List<Country> countries = countriesRepository.findAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    /**
     *
     * @param id id of the country to be updated
     * @param country the country object to be updated
     * @param Authorization This request requires Authorization Header
     * @return Updated country is returned after successful operation
     * @throws NotFoundException if the country is not founfd, this exeception is thrown
     */
    @ApiOperation(value = "Update a country with an ID")
    @PutMapping("/{id}")
    public Country updateCountry(@PathVariable Long id, @RequestBody Country country, @RequestHeader String Authorization) throws NotFoundException {
        Optional<Country> optionalCountry = countriesRepository.findById(id);
        if (optionalCountry.isPresent()){
            logger.info(String.format("updating country with id %s",id));
            optionalCountry.get().setName(country.getName());
            optionalCountry.get().setContinent(country.getContinent());
            return countriesRepository.save(optionalCountry.get());
        }
        throw new NotFoundException("Country with id "+id +"was not found");
    }

    /**
     *
     * @param id Country's Identifier for the country to be deleted
     * @param Authorization Authorization Header is needed to call this resources
     * @return Message: Response Message
     */
    @ApiOperation(value = "Delete a country")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id, @RequestHeader String Authorization){
        logger.info(String.format("Deleting country with id %s",id));
        countriesRepository.deleteById(id);
        return new ResponseEntity<>("{\"message\":\"Country deleted successfully\"}",HttpStatus.OK);
    }
}

