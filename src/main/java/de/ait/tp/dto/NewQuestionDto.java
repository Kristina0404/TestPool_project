package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Schema(name= "NewQuestion")
public class NewQuestionDto {

    @Schema(description = "Question", example = "What is an interface in Java?")
    @NotBlank
    @NotEmpty
    private String question;
}



