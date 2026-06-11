package at.uastw.disysenergyuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DisysEnergyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisysEnergyUserApplication.class, args);
    }

}
