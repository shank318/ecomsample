package co.turing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TuringApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TuringApp.class, args);
    }
}
