package com.example.stripedemosagar.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainResource<T> {

    @NonNull
    public final Status status;

    @NonNull
    public final ApiType type;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public MainResource(@NonNull Status status, @Nullable T data, @Nullable String message, @NonNull ApiType type) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.type = type;
    }

    public static <T> MainResource<T> success (@Nullable T data,  @NonNull ApiType type) {
        return new MainResource<>(Status.SUCCESS, data, null, type);
    }

    public static <T> MainResource<T> error(@NonNull String msg, @Nullable T data,  @NonNull ApiType type) {
        return new MainResource<>(Status.ERROR, data, msg, type);
    }

    public static <T> MainResource<T> loading(@Nullable T data,  @NonNull ApiType type) {
        return new MainResource<>(Status.LOADING, data, null, type);
    }

    public enum Status {SUCCESS, ERROR, LOADING}

}