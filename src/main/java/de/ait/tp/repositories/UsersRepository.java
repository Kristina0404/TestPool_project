package de.ait.tp.repositories;

import de.ait.tp.models.ConfirmationCode;

import de.ait.tp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findFirstByCodesContains(ConfirmationCode code);

    Optional<User> findUserById(Long userId);
}

