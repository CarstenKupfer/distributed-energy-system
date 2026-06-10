package at.uastw.disysenergyproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DisysEnergyProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisysEnergyProducerApplication.class, args);
    }
}
