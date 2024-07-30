package ca.cmpt213.asn5.controllers;

import ca.cmpt213.asn5.models.Tokimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class TokimonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokimonController tokimonController;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        System.out.println("Runs before each test");
    }

    @Test
    void contextLoads() {
        assertThat(tokimonController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testGetAllTokimons() throws Exception {
        mockMvc.perform(get("/tokimon/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTokimonAndGetTokimon() throws Exception {
        Tokimon newTokimon = new Tokimon("https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png", "pikachu", "electric", 6);
        MvcResult result = mockMvc.perform(post("/tokimon/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTokimon)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.imagePath", is("https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png")))
                .andExpect(jsonPath("$.name", is("pikachu")))
                .andExpect(jsonPath("$.type", is("electric")))
                .andExpect(jsonPath("$.rarityScore", is(6)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Tokimon createdTokimon = objectMapper.readValue(jsonResponse, Tokimon.class);
        long tid = createdTokimon.getTid();
        mockMvc.perform(get("/tokimon/{tid}", tid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tid", is((int) tid)))
                .andExpect(jsonPath("$.imagePath", is("https://upload.wikimedia.org/wikipedia/en/a/a6/Pok%C3%A9mon_Pikachu_art.png")))
                .andExpect(jsonPath("$.name", is("pikachu")))
                .andExpect(jsonPath("$.type", is("electric")))
                .andExpect(jsonPath("$.rarityScore", is(6)));

        // Delete the newly added Tokimon
        mockMvc.perform(delete("/tokimon/{tid}", tid))
                .andExpect(status().isNoContent());
    }


    @Test
    public void testDeleteTokimon() throws Exception {
        Tokimon delTokimon = new Tokimon("https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/007.png", "squirtle", "water", 7);
        long tid = delTokimon.getTid();

        mockMvc.perform(delete("/tokimon/{tid}", tid))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTokimon() throws  Exception {
        Tokimon updateTokimon = new Tokimon("https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/007.png", "squirtle", "water", 7);
        updateTokimon.setType("air");
        long tid = updateTokimon.getTid();
        mockMvc.perform(put("/tokimon/edit/{tid}", tid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTokimon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tid", is((int) tid)))
                .andExpect(jsonPath("$.name", is("squirtle")))
                .andExpect(jsonPath("$.type", is("air")))
                .andExpect(jsonPath("$.rarityScore", is(7)));
    }

}
