package br.com.zupacademy.guilherme.mercadolivre.repository;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
