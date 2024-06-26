package com.example.managerapp.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductPayLoad(
        @NotNull
        @Size(min = 3, max = 50)
        String title,
        @Size(max = 1000)
        String details) {
}
