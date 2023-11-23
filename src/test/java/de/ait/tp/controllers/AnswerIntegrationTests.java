package de.ait.tp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.tp.config.TestSecurityConfig;
import de.ait.tp.dto.answer.AnswerDto;
import de.ait.tp.service.AnswersService;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
@DisplayName("Endpoint /answers is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")

public class AnswerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnswersService answersService;

    @Nested
    @DisplayName("POST /answers:")
    public class addAnswer {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_answer() throws Exception {
            mockMvc.perform(post("/api/questions/{question_id}/answers", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"answer\": \"answer5\",\n" +
                                    "  \"is_correct\": \"true\",\n" +
                                    "  \"question_id\": 1\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", equalTo(25)));
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = {"/sql/data.sql"})
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_existed_answer() throws Exception {
            mockMvc.perform(post("/api/questions/{question_id}/answers", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"answer\": \"answer1\",\n" +
                                    "  \"is_correct\": \"true\",\n" +
                                    "  \"question_id\": 1\n" +
                                    "}"))
                    .andExpect(status().isConflict());

        }
    }

    @org.junit.jupiter.api.Nested
    @DisplayName("GET answers:")
    public class GetQuestion {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_answers() throws Exception {
            mockMvc.perform(get("/api/answers", 4))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(24)));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_401_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/answers", 4))
                    .andExpect(status().isUnauthorized());
        }

        @WithMockUser(value = "kristina.romanova@gmail.com")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_403_for_not_admin() throws Exception {
            mockMvc.perform(get("/api/answers", 4))
                    .andExpect(status().isForbidden());
        }

    }

    @Autowired
    private ObjectMapper objectMapper;

    @org.junit.jupiter.api.Nested
    @DisplayName("PUT /api/answers/{answer_id}:")
    public class updateTest {


        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_updated_question() throws Exception {

            AnswerDto updatedAnswer = new AnswerDto();
            updatedAnswer.setAnswer("answer10");
            updatedAnswer.setCorrect(true);
            updatedAnswer.setQuestionId(1L);
            String updatedJson = objectMapper.writeValueAsString(updatedAnswer);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/questions/1/answers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedJson))
                    .andExpect(status().isOk());
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_400_for_bad_format_type() throws Exception {
            AnswerDto updatedAnswer = new AnswerDto();
            updatedAnswer.setAnswer("");
            updatedAnswer.setCorrect(true);
            updatedAnswer.setQuestionId(1L);
            String updatedJson = objectMapper.writeValueAsString(updatedAnswer);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/questions/1/answers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedJson))
                    .andExpect(status().isBadRequest());
        }
    }

    @org.junit.jupiter.api.Nested
    @DisplayName("DELETE answers:")
    public class deleteQuestion {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_of_answers() {
            try {
                mockMvc.perform(delete("/api/questions/1/answers/1"))
                        .andExpect(status().isOk());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_404_by_delete_answer() {
            try {
                mockMvc.perform(delete("/api/questions/1/answers/11"))
                        .andExpect(status().isNotFound());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @WithUserDetails(value = "romanova@gmail.com")
    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Transactional
    public void testGetCorrectAnswerIds() {
        List<Long> correctAnswerIds = answersService.getCorrectAnswerIds(1L);

        assertNotNull(correctAnswerIds);
        assertFalse(correctAnswerIds.isEmpty());
        assertEquals(6, correctAnswerIds.size());
        assertTrue(correctAnswerIds.contains(1L));
        assertFalse(correctAnswerIds.contains(2L));
        assertFalse(correctAnswerIds.contains(3L));
        assertFalse(correctAnswerIds.contains(4L));
        assertTrue(correctAnswerIds.contains(5L));
        assertFalse(correctAnswerIds.contains(6L));
    }
}

