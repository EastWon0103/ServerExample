package com.kdw.simplecrudserver.controller.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadPostRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String author;
}
