package com.example.stripedemosagar.ui.create_card;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CreateCardResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public CreateCardResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> CreateCardResource<T> success (@Nullable T data) {
        return new CreateCardResource<>(Status.SUCCESS, data, null);
    }

    public static <T> CreateCardResource<T> error(@NonNull String msg, @Nullable T data) {
        return new CreateCardResource<>(Status.ERROR, data, msg);
    }

    public static <T> CreateCardResource<T> loading(@Nullable T data) {
        return new CreateCardResource<>(Status.LOADING, data, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}

}