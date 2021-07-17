package br.com.zupacademy.guilherme.mercadolivre;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import br.com.zupacademy.guilherme.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("dev")
public class DevMercadolivreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DevMercadolivreApplication.class, args);
	}

	@Autowired
	UserRepository repository;
	@Override
	public void run(String... args) throws Exception {
		User user = new User("tolkien@gmail.com", "123456");
		repository.save(user);
		System.out.println("[COMMAND LINE] User " + user.getUsername() + " salvo!");
	}
}
