package cc.maids.lms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cc.maids.lms.model.Patron;
import cc.maids.lms.services.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patron patron;

    @BeforeEach
    public void setup() {
        patron = new Patron();
        patron.setId("1");
        patron.setName("Test Patron");
        patron.setContactInformation("test@example.com");
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(patron));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(patron.getName()));
    }

    @Test
    public void testGetPatronById() throws Exception {
        when(patronService.getPatronById("1")).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void testAddPatron() throws Exception {
        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        when(patronService.updatePatron(any(String.class), any(Patron.class))).thenReturn(patron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patron.getName()));
    }

    @Test
    public void testDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }
}