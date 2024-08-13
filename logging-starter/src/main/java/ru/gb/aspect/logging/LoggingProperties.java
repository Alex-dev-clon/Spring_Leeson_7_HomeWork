package ru.gb.aspect.logging;

import lombok.Data;

import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.logging")
public class LoggingProperties {

    private Level level = Level.DEBUG;

    private boolean printArgs = false;

}
