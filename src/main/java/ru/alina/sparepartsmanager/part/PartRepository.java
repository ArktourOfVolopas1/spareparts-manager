package ru.alina.sparepartsmanager.part;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByArticle(String article);
    List<Part> findByCategoryIgnoreCase(String category);
    List<Part> findByNameContainingIgnoreCase(String name);
}
