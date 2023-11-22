package de.ait.tp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.tp.config.TestSecurityConfig;
import de.ait.tp.dto.tests.TestDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
@DisplayName("Endpoint /tests is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class TestIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /tests:")
    public class addTest {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        @Transactional
        public void return_created_test() throws Exception {
            mockMvc.perform(post("/api/tests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"level\": \"JUNIOR\",\n" +
                                    "  \"name\": \"new Test\",\n" +
                                    "  \"type\": \"FRONTEND\"\n" +

                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(2)));
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_409_for_existed_test() throws Exception {
            mockMvc.perform(post("/api/tests")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"level\": \"JUNIOR\",\n" +
                                    "  \"name\": \"frontend\",\n" +
                                    "  \"type\": \"FRONTEND\"\n" +

                                    "}"))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("GET /api/tests:")
    public class GetTests {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_tests() throws Exception {
            mockMvc.perform(get("/api/tests"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(1)));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_401_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/tests"))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "kristina.romanova@gmail.com")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_403_for_not_admin() throws Exception {
            mockMvc.perform(get("/api/tests"))
                    .andExpect(status().isForbidden());
        }

    }

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("PUT /api/tests:")
    public class updateTest {


        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_updated_test() throws Exception {

            TestDto updatedTest = new TestDto();
            updatedTest.setName("Frontend");
            updatedTest.setType(de.ait.tp.models.Test.Type.valueOf("FRONTEND"));
            updatedTest.setLevel(de.ait.tp.models.Test.Level.valueOf("MIDDLE"));
            String updatedJson = objectMapper.writeValueAsString(updatedTest);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedJson))
                    .andExpect(status().isOk());
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_400_for_bad_format_type() throws Exception {
            TestDto testDto = new TestDto();
            testDto.setName("Frontend");
            testDto.setType(null);
            testDto.setLevel(de.ait.tp.models.Test.Level.valueOf("MIDDLE"));
            String invalidJson = objectMapper.writeValueAsString(testDto);

            mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidJson))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("DELETE /tests:")
    public class deleteTest {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_test() {
            try {
                mockMvc.perform(delete("/api/tests/1"))
                        .andExpect(status().isOk());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_404_by_delete_test() {
            try {
                mockMvc.perform(delete("/api/tests/4"))
                        .andExpect(status().isNotFound());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}


