package br.ifg.urt.shieldnoterpgbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // cache
public class ShieldnoteRpgBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShieldnoteRpgBoxApplication.class, args);
	}

}