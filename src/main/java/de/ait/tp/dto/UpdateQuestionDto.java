package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateQuestion",description = "Question update " )
public class UpdateQuestionDto {

    @Schema(description = "Question_ID", example = "11")
    @Positive
    private Long id;
    @Schema(description = "Question", example = "What is a.....?")
    @NotNull
    @NotBlank
    @NotEmpty
    private String question;
    @Schema(description = "Test_ID", example = "5")
    @Positive
    @NotNull
    private Long testId;


}


