package ru.alina.sparepartsmanager.part;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Page<Part> findAll(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    public List<Part> findAll() {
        return partRepository.findAll();
    }

    public Part findById(Long id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException(id));
    }

    public Part create(Part part) {
        return partRepository.save(part);
    }

    public Part update(Long id, Part updatedPart) {
        Part part = findById(id);
        part.setName(updatedPart.getName());
        part.setArticle(updatedPart.getArticle());
        part.setCategory(updatedPart.getCategory());
        part.setPrice(updatedPart.getPrice());
        part.setQuantity(updatedPart.getQuantity());
        return partRepository.save(part);
    }

    public void delete(Long id) {
        Part part = findById(id);
        partRepository.delete(part);
    }
    public Part findByArticle(String article) {
        return partRepository.findByArticle(article)
                .orElseThrow(() ->
                        new PartNotFoundException(-1L));
    }

    public List<Part> findByCategory(String category) {
        return partRepository.findByCategoryIgnoreCase(category);
    }
    public List<Part> findByName(String name) {
        return partRepository.findByNameContainingIgnoreCase(name);
    }
}