package com.back.domain.post.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostWriteForm(
        @NotBlank
        @Size(min = 2, max = 100)
        String title,
        @NotBlank
        @Size(min = 2, max = 100)
        String content
) {
}
