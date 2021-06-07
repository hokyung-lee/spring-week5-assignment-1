package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 사용자 관련 Jpa를 처리하는 인터페이스.
 */
@Primary
public interface JpaUserRepository
        extends UserRepository, CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);
}