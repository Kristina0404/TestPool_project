package de.ait.tp.controllers.api;

import de.ait.tp.dto.*;
import de.ait.tp.dto.answer.AllAnswersDto;
import de.ait.tp.dto.answer.AnswerDto;
import de.ait.tp.dto.answer.NewAnswerDto;
import de.ait.tp.dto.answer.UpdateAnswerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiResponse(responseCode = "401",
        description = "Admin unauthorized",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = StandardResponseDto.class)))

@Tags(value = @Tag(name = "Answers"))
@Schema(name = "Answer", description = "Answers with questions_id")

public interface AnswersApi {

    @Operation(summary = "Adding a answer", description = "Available to admin ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Answer added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "An answer in this question already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))

    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/questions/{question_id}/answers")
    AnswerDto addAnswerToQuestion(@PathVariable("question_id") Long questionId,
                                  @RequestBody NewAnswerDto newAnswer);


    @Operation(summary = "Receiving all answers", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllAnswersDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Answers not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/api/answers")
    List<AnswerDto> getAllAnswers();

    @Operation(summary = "Answer update", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateAnswerDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Question or answer not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "The request was made incorrectly",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "An answer in this question already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @PutMapping("/api/questions/{question_id}/answers/{answer_id}")
    AnswerDto updateAnswerInQuestion(@PathVariable("question_id") Long questionId,
                                     @PathVariable("answer_id") Long answerId,
                                     @RequestBody @Valid UpdateAnswerDto updateAnswer);


    @Operation(summary = "Delete Answer", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Question or answer not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @DeleteMapping("/api/questions/{question_id}/answers/{answer_id}")
    AnswerDto deleteAnswerFromQuestion(@PathVariable("question_id") Long courseId,
                                       @PathVariable("answer_id") Long lessonId);

    @Operation(summary = "Correct answer", description = "Available to admin and user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDto.class))
            ),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/api/questions/{question_id}/answers/{answer_id}")
    ResponseEntity<AnswerDto> getCorrectAnswer(
           // @PathVariable("question_id")  Long question_id,
            @PathVariable("answer_id") Long selectedAnswerId);

}
