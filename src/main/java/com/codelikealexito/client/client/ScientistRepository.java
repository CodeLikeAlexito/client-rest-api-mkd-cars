package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Scientist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScientistRepository extends JpaRepository<Scientist, Long> {
    Scientist findByUsername(String username);
    Scientist findByEmail(String email);
}
