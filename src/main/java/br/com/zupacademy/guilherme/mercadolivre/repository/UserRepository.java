package br.com.zupacademy.guilherme.mercadolivre.repository;

import br.com.zupacademy.guilherme.mercadolivre.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
