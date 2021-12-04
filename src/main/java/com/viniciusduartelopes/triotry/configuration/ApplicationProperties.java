package com.viniciusduartelopes.triotry.configuration;

import java.util.logging.Level;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Log
public class ApplicationProperties {

    @Value("${auth}")
    @Getter
    private String auth;

    @Value("${listName}")
    @Getter
    private String listName;

    @Value("${mockAPIUrl}")
    @Getter
    private String mockAPIUrl;

    @Value("${mailChimpUrl}")
    @Getter
    private String mailChimpUrl;

    @PostConstruct
    public void init() {
        log.log(Level.INFO, "Successfully started configuration bean.");
    }
}
