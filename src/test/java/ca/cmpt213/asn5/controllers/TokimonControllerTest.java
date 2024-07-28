package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import ca.cmpt213.asn5.models.TokimonList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TokimonController.class)
class TokimonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokimonList tokimonList;

    @Autowired
    private ObjectMapper objectMapper;

    private Tokimon tokimon;

    @BeforeEach
    public void setup() {
        tokimon = new Tokimon("https://images.app.goo.gl/3dWRCMEXLvGaHyKS8", "pikachu", "electric",6);
        tokimon.setTid(1);
    }

    @Test
    public void testGetTokimonList() throws Exception {
        List<Tokimon> allTokimons = Collections.singletonList(tokimon);
        when(tokimonList.getTokimons()).thenReturn(allTokimons);

        mockMvc.perform(get("/tokimon/all"))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetTokimon() throws Exception {
        when(tokimonList.getTokimonWithTid(1)).thenReturn(tokimon);

        mockMvc.perform(get("/tokimon/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTokimon() throws Exception {
        Tokimon newTokimon = new Tokimon("https://images.app.goo.gl/DZkWVKU37ZY4bGkK7", "snorlax", "normal", 8);
        mockMvc.perform(post("/tokimon/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTokimon)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteTokimon() throws Exception {
        doNothing().when(tokimonList).deleteTokimon(1);

        mockMvc.perform(delete("/tokimon/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTokimon() throws Exception {
        Tokimon updatedTokimon = new Tokimon("https://images.app.goo.gl/QqKzkXpCaHVk3XYV6", "richu", "electric", 10);
        updatedTokimon.setTid(1);
        updatedTokimon.setName("richu");

        mockMvc.perform(put("/tokimon/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTokimon)))
                .andExpect(status().isOk());
    }
}