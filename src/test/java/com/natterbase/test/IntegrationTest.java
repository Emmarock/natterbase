package com.natterbase.test;


import com.natterbase.test.model.ApplicationUser;
import com.natterbase.test.model.Country;
import com.natterbase.test.model.Login;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {


    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();



    @Test
    public void testCreateUser() {
        ResponseEntity<String> response = createUser();
        int statusCode = response.getStatusCode().value();
        assertEquals("User created successfully", 200,statusCode);
    }

    private ResponseEntity<String> createUser() {
        ApplicationUser user = new ApplicationUser();
        user.setDateOfBirth("19/01/1900");
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("babajide");
        user.setLastName("Apata");
        user.setEmail("email@mail.com");
        HttpEntity<ApplicationUser> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(
                createURLWithPort("/users/sign-up"),
                HttpMethod.POST, entity, String.class);
    }

    @Test
    public void testUserLogin() {
        ResponseEntity<String> response = login();
        String expected = response.getHeaders().get("Authorization").get(0);
        assertNotNull(expected);
        TestCase.assertEquals(response.getStatusCode().value(),200);
        assertNotNull(response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
    }


    @Test
    public void testUserXCreateCountryAfterLoginAndGetCountry() {
        ResponseEntity<String> token = login();
        ResponseEntity<Country> response = saveCountry(token);
        Country expected = response.getBody();
        assertNotNull(expected);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getContinent(),"Africa");
        assertEquals(response.getBody().getName(),"Nigeria");
        assertNotNull(response.getBody().getCreated());
        HttpEntity<Country> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Country>> getCountries = restTemplate.exchange(createURLWithPort("/countries/"),
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Country>>(){} );
        List<Country> expectedCountry = getCountries.getBody();
        assertNotNull(expectedCountry);
        assertEquals(getCountries.getStatusCodeValue(),200);
        assertEquals(getCountries.getBody().get(0).getContinent(),"Africa");
        assertEquals(getCountries.getBody().get(0).getName(),"Nigeria");
        assertNotNull(getCountries.getBody().get(0).getCreated());
    }


    private ResponseEntity<String> login() {
        Login login = new Login();
        login.setUsername("username");
        login.setPassword("password");
        headers.add("Content-Type","application/json");
        HttpEntity<Login> entity = new HttpEntity<>(login, headers);
        return restTemplate.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, entity, String.class);
    }

    private ResponseEntity<Country> saveCountry(ResponseEntity<String> token) {
        Country country = new Country();
        country.setName("Nigeria");
        country.setContinent("Africa");
        headers.add("Content-Type","application/json");
        headers.add(HttpHeaders.AUTHORIZATION,token.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        HttpEntity<Country> entity = new HttpEntity<>(country, headers);
        return restTemplate.exchange(
                createURLWithPort("/countries/"),
                HttpMethod.POST, entity, Country.class);
    }



    /**
     * Integration test for getting Country after user must have log in
     */

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
