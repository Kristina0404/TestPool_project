package de.ait.tp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.tp.config.TestSecurityConfig;
import de.ait.tp.dto.question.QuestionDto;
import de.ait.tp.dto.question.QuestionWithCorrectAnswerDto;
import de.ait.tp.models.Answer;
import de.ait.tp.models.Question;
import de.ait.tp.models.QuestionWithCorrectAnswer;
import de.ait.tp.repositories.AnswersRepository;
import de.ait.tp.repositories.QuestionsRepository;
import de.ait.tp.repositories.TestsRepository;
import de.ait.tp.service.QuestionsService;
import de.ait.tp.service.impl.QuestionsServiceImpl;
import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc

@DisplayName("Endpoint /questions is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")

public class QuestionIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    QuestionsService questionsService;
    @Mock
    QuestionsRepository questionsRepository;
    @Mock
    AnswersRepository answersRepository;

    @Nested
    @DisplayName("POST /questions:")
    public class addQuestion {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_created_question() throws Exception {
            mockMvc.perform(post("/api/tests/{test_id}/questions", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"question\": \"What is b\",\n" +
                                    "  \"test_id\": 1\n" +
                                    "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", equalTo(7)));
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = {"/sql/data.sql"})
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_existed_question() throws Exception {
            mockMvc.perform(post("/api/tests/{test_id}/questions", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "  \"question\": \"What is a?\",\n" +
                                    "  \"testId\": 1\n" +
                                    "}"))
                    .andExpect(status().isConflict());

        }
    }

    @Nested
    @DisplayName("GET all questions:")
    public class GetQuestion {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_questions() throws Exception {
            mockMvc.perform(get("/api/questions", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(6)));
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_401_for_unauthorized() throws Exception {
            mockMvc.perform(get("/api/tests/{test_id}/questions", 1))
                    .andExpect(status().isUnauthorized());
        }

        @WithUserDetails(value = "kristina.romanova@gmail.com")
        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_403_for_not_admin() throws Exception {
            mockMvc.perform(get("/api/questions", 1))
                    .andExpect(status().isForbidden());
        }

    }

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("PUT /questions:")
    public class updateQuestion {


        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_updated_question() throws Exception {

            QuestionDto updatedQuestion = new QuestionDto();
            updatedQuestion.setQuestion("What is w?");
            updatedQuestion.setTestId(1L);
            String updatedJson = objectMapper.writeValueAsString(updatedQuestion);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/1/questions/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedJson))
                    .andExpect(status().isOk());
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_400_for_bad_format_type() throws Exception {
            QuestionDto updatedQuestion = new QuestionDto();
            updatedQuestion.setQuestion("");
            updatedQuestion.setTestId(1L);
            String updatedJson = objectMapper.writeValueAsString(updatedQuestion);
            mockMvc.perform(MockMvcRequestBuilders.put("/api/tests/1/questions/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedJson))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE questions:")
    public class deleteQuestion {

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_empty_test() {
            try {
                mockMvc.perform(delete("/api/tests/1/questions/1"))
                        .andExpect(status().isOk());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @WithUserDetails(value = "romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_404_by_delete_question() {
            try {
                mockMvc.perform(delete("/api/tests/2/questions/111"))
                        .andExpect(status().isNotFound());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("GET randomQuestions:")
    public class getAllQuestionIdsTests {

        @WithUserDetails(value = "kristina.romanova@gmail.com")
        @Test
        @Sql(scripts = "/sql/data.sql")
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
        public void return_list_of_random_questions() throws Exception {

            List<Long> questions = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L);

            QuestionsRepository questionsRepository = mock(QuestionsRepository.class);
            when(questionsRepository.getAllQuestionIdsByTestId(anyLong())).thenReturn(questions);
            mockMvc.perform(get("/api/tests/1/questions/randomQuestions"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(questions.size())));
        }
    }


  /*  @WithUserDetails(value = "kristina.romanova@gmail.com")
    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)

  /*  public void testGetCorrectAnswerByQuestionId() throws Exception {
        // MockitoAnnotations.initMocks(this);
        Question question = new Question();
        question.setId(1L);
        question.setQuestion("What is a?");

        Answer correctAnswer = new Answer();
        correctAnswer.setId(1L);
        correctAnswer.setAnswer("answer1");
        correctAnswer.setCorrect(true);


        // Настраиваем поведение мок-репозиториев
        // Mockito.when(questionsRepository.getById(1L)).thenReturn(question);
        //Mockito.when(answersRepository.findByQuestionIdAndIsCorrectTrue(1L)).thenReturn(Collections.singletonList(correctAnswer));

        // Вызываем метод вашего сервиса для тестирования
      //  QuestionWithCorrectAnswerDto resultDto = questionsService.getCorrectAnswerByQuestionId(1L);

        // Добавьте здесь свои проверки, используя assert или другие средства тестирования
        // Например:
        /* assertEquals(1L, resultDto.getQuestionId());
         assertEquals(1L, resultDto.getCorrectAnswerId());
         assertEquals("What is a?", resultDto.getQuestionText());
         assertEquals("answer1", resultDto.getCorrectAnswerText());*/

        //List<Long> correctAnswerIds = answersService.getCorrectAnswerIds(1L);

       /* assertNotNull(resultDto);
        assertEquals(resultDto,1L);
        assertEquals(resultDto,"answer1");
        assertNotEquals(2L, resultDto);
        assertNotEquals("answer2", resultDto);

        assertNotEquals(3L, resultDto);
        assertNotEquals("answer3", resultDto);

        assertNotEquals(4L, resultDto);
        assertNotEquals("answer4", resultDto);*/
     /*   Mockito.when(questionsRepository.getById(1L)).thenReturn(question);
        Mockito.when(answersRepository.findByQuestionIdAndIsCorrectTrue(1L)).thenReturn(Collections.singletonList(correctAnswer));

// Вызываем метод вашего сервиса для тестирования
        QuestionWithCorrectAnswerDto resultDto = questionsService.getCorrectAnswerByQuestionId(1L);

// Проверки
        assertEquals(1L, resultDto.getQuestionId());
        assertEquals("answer1", resultDto.getCorrectAnswerText());

        assertNotEquals(2L, resultDto.getQuestionId());
        assertNotEquals("answer2", resultDto.getCorrectAnswerText());

        assertNotEquals(3L, resultDto.getQuestionId());
        assertNotEquals("answer3", resultDto.getCorrectAnswerText());

        assertNotEquals(4L, resultDto.getQuestionId());
        assertNotEquals("answer4", resultDto.getCorrectAnswerText());

    }*/
   /* public void testGetCorrectAnswerByQuestionId() {
            Long questionId = 1L;

    Question question = new Question();
    question.setId(questionId);
    question.setQuestion("What is a?");

    Answer correctAnswer = new Answer();
    correctAnswer.setId(1L);
    correctAnswer.setAnswer("answer1");
    correctAnswer.setCorrect(true);
    correctAnswer.setQuestion(question);

    // Настраиваем мок-сервисы
    QuestionsRepository questionsRepositoryMock = mock(QuestionsRepository.class);
    AnswersRepository answersRepositoryMock = mock(AnswersRepository.class);
    QuestionsService questionsService = new QuestionsServiceImpl(questionsRepositoryMock, answersRepositoryMock);

    // Настраиваем поведение мок-репозиториев
    when(questionsRepositoryMock.getById(questionId)).thenReturn(question);
    when(answersRepositoryMock.findByQuestionIdAndIsCorrectTrue(questionId)).thenReturn(Collections.singletonList(correctAnswer));

    // Вызываем метод вашего сервиса для тестирования
    QuestionWithCorrectAnswerDto resultDto = questionsService.getCorrectAnswerByQuestionId(questionId);

    // Добавьте здесь свои проверки, используя assert или другие средства тестирования
    assertNotNull(resultDto);
    assertEquals(questionId, resultDto.getQuestionId());
    assertEquals(correctAnswer.getId(), resultDto.getCorrectAnswerId());
    assertEquals(question.getQuestion(), resultDto.getQuestionText());
    assertEquals(correctAnswer.getAnswer(), resultDto.getCorrectAnswerText());
}*/

}







