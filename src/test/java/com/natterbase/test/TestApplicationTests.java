package com.natterbase.test;

import com.natterbase.test.model.Country;
import com.natterbase.test.repository.CountriesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    private CountriesRepository countriesRepository ;

    private static Country country;
    private static Country country1;
    private static Country saveCountry;
    private static Country saveCountry1;

    @Before
    public  void setUp(){
        country = new Country();
        country.setContinent("Africa");
        country.setName("Nigeria");
        country1 = new Country();
        country1.setContinent("Europe");
        country1.setName("Spain");
        saveCountry = countriesRepository.save(country);
        saveCountry1 = countriesRepository.save(country1);
    }


    @Test
    public void testCreateCountries(){
        assertThat(saveCountry.getName()).isEqualTo(country.getName());
        assertThat(saveCountry.getContinent()).isEqualTo(country.getContinent());
        assertThat(saveCountry1.getName()).isEqualTo(country1.getName());
        assertThat(saveCountry1.getContinent()).isEqualTo(country1.getContinent());
    }
    @Test
    public void getCountry(){
        Optional<Country> getCountry = countriesRepository.findById(1L);
        assertThat(getCountry.isPresent()).isEqualTo(true);
        getCountry.ifPresent(country2 -> assertThat(country2.getContinent()).isEqualTo("Africa"));
    }

    @Test
    public void deleteCountry(){
        countriesRepository.deleteById(2L);
        Optional<Country> getCountry = countriesRepository.findById(2L);
        assertThat(getCountry.isPresent()).isEqualTo(false);
    }
}
