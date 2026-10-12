package ru.alina.sparepartsmanager.part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks; import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) class PartServiceTest {
    @Mock
    private PartRepository partRepository;

    @InjectMocks
    private PartService partService;

    private Part part;

    @BeforeEach
    void setUp() {
        part = new Part();
        part.setName("Масляный фильтр");
        part.setArticle("FILTER-001");
        part.setCategory("Фильтры");
        part.setPrice(new BigDecimal("450.00"));
        part.setQuantity(25);
    }

    @Test
    void findAllReturnsParts() {
        when(partRepository.findAll()).thenReturn(List.of(part));

        List<Part> result = partService.findAll();

        assertEquals(1, result.size());
        assertEquals("FILTER-001", result.get(0).getArticle());
        verify(partRepository).findAll();
    }

    @Test
    void findByIdReturnsPartWhenItExists() {
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        Part result = partService.findById(1L);

        assertEquals("Масляный фильтр", result.getName());
        verify(partRepository).findById(1L);
    }

    @Test
    void findByIdThrowsExceptionWhenPartDoesNotExist() {
        when(partRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                PartNotFoundException.class,
                () -> partService.findById(999L)
        );
    }

    @Test
    void createSavesPart() {
        when(partRepository.save(part)).thenReturn(part);

        Part result = partService.create(part);

        assertEquals("FILTER-001", result.getArticle());
        verify(partRepository).save(part);
    }

    @Test
    void deleteRemovesExistingPart() {
        when(partRepository.findById(1L)).thenReturn(Optional.of(part));

        partService.delete(1L);

        verify(partRepository).delete(part);
    }

    @Test
    void findByCategoryReturnsMatchingParts() {
        when(partRepository.findByCategoryIgnoreCase("Фильтры"))
                .thenReturn(List.of(part));

        List<Part> result = partService.findByCategory("Фильтры");

        assertEquals(1, result.size());
        assertEquals("Фильтры", result.get(0).getCategory());
    }

    @Test void findByNameReturnsMatchingParts() { Part part = new Part(); part.setName("Масляный фильтр"); part.setArticle("FILTER-001"); part.setCategory("Фильтры"); part.setPrice(new BigDecimal("450.00")); part.setQuantity(25);
        when(partRepository.findByNameContainingIgnoreCase("фильтр"))
                .thenReturn(List.of(part));

        List<Part> result = partService.findByName("фильтр");

        assertEquals(1, result.size());
        assertEquals("Масляный фильтр", result.get(0).getName());
        verify(partRepository).findByNameContainingIgnoreCase("фильтр");
    }
    @Test void findByNameReturnsEmptyListWhenNoMatches() { when(partRepository.findByNameContainingIgnoreCase("несуществующая")) .thenReturn(List.of());
        List<Part> result = partService.findByName("несуществующая");

        assertTrue(result.isEmpty());
    }

    @Test
    void findByArticleReturnsPartWhenItExists() {
        when(partRepository.findByArticle("FILTER-001"))
                .thenReturn(Optional.of(part));

        Part result = partService.findByArticle("FILTER-001");

        assertEquals("Масляный фильтр", result.getName());
    }
    @Test void updateChangesExistingPart() { when(partRepository.findById(1L)).thenReturn(Optional.of(part)); when(partRepository.save(part)).thenReturn(part);
        Part updatedPart = new Part();
        updatedPart.setName("Воздушный фильтр");
        updatedPart.setArticle("FILTER-002");
        updatedPart.setCategory("Фильтры");
        updatedPart.setPrice(new BigDecimal("600.00"));
        updatedPart.setQuantity(10);

        Part result = partService.update(1L, updatedPart);

        assertEquals("Воздушный фильтр", result.getName());
        assertEquals("FILTER-002", result.getArticle());
        assertEquals(new BigDecimal("600.00"), result.getPrice());
        assertEquals(10, result.getQuantity());

        verify(partRepository).save(part);
    }
}