package com.vastinc.thecatexperience.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ResponseResource<T> {
    @NonNull
    public final ResponseStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public ResponseResource(@NonNull ResponseStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseResource<T> success (@Nullable T data) {
        return new ResponseResource<>(ResponseStatus.SUCCESS, data, null);
    }

    public static <T> ResponseResource<T> error(@NonNull String msg, @Nullable T data) {
        return new ResponseResource<>(ResponseStatus.ERROR, data, msg);
    }

    public static <T> ResponseResource<T> loading(@Nullable T data) {
        return new ResponseResource<>(ResponseStatus.LOADING, data, null);
    }

    public static <T> ResponseResource<T> logout () {
        return new ResponseResource<>(ResponseStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum ResponseStatus { SUCCESS, ERROR, LOADING, NOT_AUTHENTICATED}
}
