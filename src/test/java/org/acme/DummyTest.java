package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.HashSet;
import org.eclipse.microprofile.jwt.Claims;
import org.junit.jupiter.api.Test;
import io.smallrye.jwt.build.Jwt;

@QuarkusTest
public class DummyTest {

    @Test
    void hello() {
        given()
            .when()
            .headers(
                "Authorization",
                "Bearer " + generateToken(),
                "Content-Type",
                ContentType.JSON,
                "Accept",
                ContentType.JSON)
            .get("/dummy")
            .then()
            .statusCode(200)
            .body(is("world"));
    }

    private String generateToken() {
        return Jwt.issuer("https://example.com/issuer")
                .upn("jdoe@quarkus.io")
                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .sign();
    }

}
