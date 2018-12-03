package bettercare.wic.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigurationTest {

    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;

    @Before
    public void setup() throws MalformedURLException {

        base = new URL("http://localhost:" + port + "/admin/users");
    }

    @Test
    public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException {
        restTemplate = new TestRestTemplate("admin", "admin");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(base.toExternalForm(), String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}
