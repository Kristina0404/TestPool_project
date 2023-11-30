package de.ait.tp.dto.tests;

import de.ait.tp.models.TestResult;
import de.ait.tp.models.TestTotalResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TestTotalResultDto {

    @Schema(description = "Test_Result_ID", example = "1")
    private Long id;
    @Schema(description = "User_ID", example = "3")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long userId;
    @Schema(description = "total result score", example = "100")
    @NotBlank
    @NotEmpty
    private int totalCorrectAnswer;
    @Schema(description = "number of test", example = "20")
    @NotNull
    @NotBlank
    @NotEmpty
    private int testTaken;

    public static TestTotalResultDto from(TestTotalResult testTotalResult) {
        return TestTotalResultDto.builder()
                .id(testTotalResult.getId())
                .userId(testTotalResult.getUser().getId())
                .totalCorrectAnswer(testTotalResult.getTotalCorrectAnswer())
                .testTaken(testTotalResult.getTestTaken())
                .build();
    }


    public static List<TestResultDto> from(Collection<TestResult> testResults) {
        return testResults.stream()
                .map(TestResultDto::from).collect(Collectors.toList());
    }

}
