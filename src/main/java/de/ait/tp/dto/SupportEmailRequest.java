package de.ait.tp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportEmailRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    private String senderEmail;
    private String subject;
    @NotNull
    @NotEmpty
    @NotBlank
    private String text;


}
