package ru.alina.sparepartsmanager.part;

import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartController.class) @Import(PartExceptionHandler.class) class PartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PartService partService;

    @Test
    void findAllReturnsParts() throws Exception {
        Part part = createPart();
        when(partService.findAll()).thenReturn(List.of(part));

        mockMvc.perform(get("/api/parts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Масляный фильтр"))
                .andExpect(jsonPath("$[0].article").value("FILTER-001"));
    }

    @Test
    void createReturnsCreatedStatus() throws Exception {
        Part part = createPart();
        when(partService.create(any(Part.class))).thenReturn(part);

        mockMvc.perform(post("/api/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(part)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article").value("FILTER-001"));
    }

    @Test
    void createReturnsBadRequestWhenNameIsBlank() throws Exception {
        String json = """
            {
                "name": "",
                "article": "FILTER-TEST",
                "category": "Фильтры",
                "price": 450.00,
                "quantity": 25
            }
            """;

        mockMvc.perform(post("/api/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

        verifyNoInteractions(partService);
    }

    @Test
    void findByIdReturnsNotFoundWhenPartDoesNotExist() throws Exception {
        when(partService.findById(999999L))
                .thenThrow(new PartNotFoundException(999999L));

        mockMvc.perform(get("/api/parts/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    private Part createPart() {
        Part part = new Part();
        part.setName("Масляный фильтр");
        part.setArticle("FILTER-001");
        part.setCategory("Фильтры");
        part.setPrice(new BigDecimal("450.00"));
        part.setQuantity(25);
        return part;
    }
}