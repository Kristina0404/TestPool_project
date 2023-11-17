package de.ait.tp.dto;

import de.ait.tp.models.TestResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Array of Test Results", example = "[{\"id\": 1," +
        " \"userId\": 3, \"testId\": 5, \"score\": 4," +
        " \"date\": \"2024-01-01\", \"progressPercent\": 50}]")
public class TestResultDto {

    @Schema(description = "Test_Result_ID")
    private Long id;
    @Schema(description = "User id")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long userId;
    @Schema(description = "Test id")
    @NotNull
    @NotBlank
    @NotEmpty
    private Long testId;
    @Schema(description = "score")
    @NotNull
    @NotBlank
    @NotEmpty
    private int score;
    @Schema(description = "Test date")
    private LocalDate date;
    @Schema(description = "Percentage progress indicator")
    private double  progressPercent;

    public static TestResultDto from(TestResult testResult) {
        return TestResultDto.builder()
                .id(testResult.getId())
                .userId(testResult.getUser().getId())
                .testId(testResult.getTest().getId())
                .score(testResult.getScore())
                .date(testResult.getDate())
                .progressPercent(testResult.getProgressPercent())
                .build();
    }


    public static List<TestResultDto> from(Collection<TestResult> testResults){
        return testResults.stream()
                .map(TestResultDto::from).collect(Collectors.toList());
    }

}
