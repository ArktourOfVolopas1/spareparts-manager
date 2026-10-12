package ru.alina.sparepartsmanager.part;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@RestController @RequestMapping("/api/parts") public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> findAll() {
        return partService.findAll();
    }

    @GetMapping("/{id}")
    public Part findById(@PathVariable Long id) {
        return partService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Part create(@Valid @RequestBody Part part) {
        return partService.create(part);
    }

    @PutMapping("/{id}")
    public Part update(
            @PathVariable Long id,
            @Valid @RequestBody Part part
    ) {
        return partService.update(id, part);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        partService.delete(id);
    }

    @GetMapping("/search")
    public List<Part> findByName(@RequestParam String name) {
        return partService.findByName(name);
    }

    @GetMapping("/article/{article}")
    public Part findByArticle(@PathVariable String article) {
        return partService.findByArticle(article);
    }

    @GetMapping("/page") public Page<Part> findAllPaged( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return partService.findAll(pageable);
    }

    @GetMapping("/category/{category}")
    public List<Part> findByCategory(@PathVariable String category) {
        return partService.findByCategory(category);
    }
}