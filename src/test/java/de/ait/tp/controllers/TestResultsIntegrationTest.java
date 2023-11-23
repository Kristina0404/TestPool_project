package de.ait.tp.controllers;

import de.ait.tp.config.TestSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestSecurityConfig.class)
@RequiredArgsConstructor
@AutoConfigureMockMvc
@DisplayName("Endpoint /tests is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class TestResultsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /save tests:")

    public class calculateAndSaveTestResult {

        @WithUserDetails(value = "kristina.romanova@gmail.com")
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @org.junit.jupiter.api.Test
        void return_calculated_and_saved_test() throws Exception {

            mockMvc.perform(post(
                            "/api/tests/{test_id}/saveResult", 1, 2, 3)
                            .param("userId", String.valueOf(1))
                            .param("test_id", String.valueOf(1))
                            .param("maxPoints", String.valueOf(3))
                            .param("userAnswers", String.valueOf(2))

                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"score\": 3,\n" +
                                    "  \"test_id\": 1,\n" +
                                    "  \"user_id\": 1,\n" +
                                    "  \"date\": \"2023-01-01\",\n" +
                                    "  \"progress_percent\": 75\n" +
                                    "}"))
                    .andExpect(status().isOk());
        }

    }
    @Nested
    @DisplayName("GET test results for user :")
    public class GetTestResultsTests {

        @WithUserDetails(value = "kristina.romanova@gmail.com")
        @org.junit.jupiter.api.Test
        @Sql(scripts = "/sql/data2.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_test_results_for_user() throws Exception {
            mockMvc.perform(get("/api/testResults/users/{user_id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)));
        }

        @org.junit.jupiter.api.Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_401_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/testResults/users/{user_id}", 1L))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_403_for_not_admin() throws Exception {
            mockMvc.perform(get("/api/testResults/users/{user_id}", 1L))
                    .andExpect(status().isForbidden());
        }

    }

}

