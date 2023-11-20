package de.ait.tp.controllers.api;

import de.ait.tp.dto.AnswerDto;
import de.ait.tp.dto.StandardResponseDto;
import de.ait.tp.dto.SupportEmailRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tags(value = @Tag(name = "Support"))
@Schema(name = "Support", description = "Contact with admin")
public interface SupportApi {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDto.class))
            ),
            @ApiResponse(responseCode = "401",
                    description = "User unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @PostMapping("/api/sendEmail")
    String sendSupportEmail(@RequestBody SupportEmailRequest emailRequest);
}
