package br.com.zupacademy.guilherme.mercadolivre.user.repository;

import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
