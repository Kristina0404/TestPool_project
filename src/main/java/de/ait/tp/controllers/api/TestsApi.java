package de.ait.tp.controllers.api;

import de.ait.tp.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(responseCode = "401",
                description = "Admin unauthorized",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class))),
        @ApiResponse(responseCode = "403",
                description = "Forbidden, only admin available",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class)))})
@Tags(value = @Tag(name = "Tests"))
@Schema(name = "Test", description = "Test types")
public interface TestsApi {
    @Operation(summary = "Adding a test", description = "Available to admin ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Test added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "A test already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/tests")
    TestDto addTest(@RequestBody NewTestDto newTest);

    @Operation(summary = "Receiving all tests", description = "Available all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AllTestsDto.class))),

    })
    @GetMapping("/api/tests")
    List<TestDto> getAllTests();

    @Operation(summary = "Test update", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateTestDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Test not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "The request was made incorrectly",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "A test already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))
            )
    })
    @PutMapping("/api/tests/{test_id}")
    TestDto updateTest(@PathVariable("test_id") Long testId,
                       @RequestBody @Valid UpdateTestDto updateTest);

    @Operation(summary = "Delete Test", description = "Valid after deleting all questions." +
            "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TestDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Test not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @DeleteMapping("api/tests/{test_id}")
    TestDto deleteTest(@PathVariable("test_id") Long testId);


}
