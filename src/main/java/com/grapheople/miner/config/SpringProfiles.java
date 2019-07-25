package com.grapheople.miner.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@Component
public class SpringProfiles implements InitializingBean {
    private Environment environment;

    @Autowired
    public SpringProfiles(Environment environment) {
        this.environment = environment;
    }

    public PhaseType getCurrentPhase() {
        String[] profiles = environment.getActiveProfiles();

        if (profiles == null || profiles.length == 0) {
            return PhaseType.local;
        }

        return Stream.of(PhaseType.values())
                .filter(e -> e.name().equals(profiles[0]))
                .findFirst().orElse(PhaseType.local);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Current Profile is {}", getCurrentPhase());
    }
}
