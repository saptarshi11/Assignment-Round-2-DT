package dev.dt.pickspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import java.util.Map;

import dev.dt.pickspot.dto.PickRequest;
import dev.dt.pickspot.dto.PickResponse;
import dev.dt.pickspot.service.PickerService;

import static org.springframework.http.ResponseEntity.*;

@SpringBootApplication
@RestController
public class PickSpotApp {

    public static void main(String[] args) {
        SpringApplication.run(PickSpotApp.class, args);
    }

    // Add CORS configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    @Autowired
    private PickerService picker;

    @PostMapping("/pickSpot")
    @CrossOrigin
    public ResponseEntity<?> pick(@RequestBody PickRequest req) {
        return picker.chooseBestSlot(req.container(), req.yardMap())
                .<ResponseEntity<?>>map(s -> ok(new PickResponse(req.container().id(), s.x(), s.y())))
                .orElseGet(() -> badRequest().body(Map.of("error", "no suitable slot")));
    }
}
