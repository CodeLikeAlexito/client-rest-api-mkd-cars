package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScientistRepository extends JpaRepository<Scientist, Long> {
    Optional<Scientist> findByUsername(String username);
    Optional<Scientist> findByEmail(String email);
}
