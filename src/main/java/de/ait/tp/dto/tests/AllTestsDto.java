package de.ait.tp.dto.tests;

import de.ait.tp.models.Test;
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
@Schema(description = "Array of all tests", example = "[{\"id\": 1," +
        " \"name\": \"Backend for juniors\", \"type\": \"BACKEND\", \"level\": \"JUNIOR\"}]")
public class AllTestsDto {

    @Schema(description = "Test_ID")
    private Long id;
    @Schema(description = "Test name")
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @Schema(description = "Course type")
    @NotNull
    @NotBlank
    private Test.Type type;
    @Schema(description = "Knowledge level")
    @NotNull
    private Test.Level level;

    public static TestDto from(Test test) {
        return TestDto.builder()
                .id(test.getId())
                .name(test.getName())
                .type(test.getType())
                .level(test.getLevel())
                .build();
    }

    public static List<TestDto> from(Collection<Test> tests) {
        return tests.stream()
                .map(TestDto::from).collect(Collectors.toList());
    }

}

