package com.natterbase.test.repository;

import com.natterbase.test.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepository  extends JpaRepository<Country, Long> {

}
