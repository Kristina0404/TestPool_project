package de.ait.tp.controllers.api;

import de.ait.tp.dto.StandardResponseDto;
import de.ait.tp.dto.tests.TestResultDto;
import de.ait.tp.dto.tests.TestTotalResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiResponse(responseCode = "401",
        description = "User unauthorized",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = StandardResponseDto.class)))

@Tags(value = @Tag(name = "TestResults"))
@Schema(name = "TestResults", description = "Test results")
public interface TestResultsApi {
    @Operation(summary = "Save test result for user")
    @ApiResponse(responseCode = "200", description = "Request processed successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TestTotalResultDto.class)))
    @PostMapping("/api/tests/{test_id}/saveResult")
    TestTotalResultDto calculateAndSaveTestResult(
            @PathVariable("test_id") Long testId,
            @RequestParam List<Long> userAnswers,
            Authentication authentication);

    @Operation(summary = "Get tests results for user ",
            description = "Available to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestResultDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Results not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only user available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/api/testResults/users/{user_id}")
    List<TestResultDto> getTestResultsForUser(@PathVariable("user_id") Long userId);
}
