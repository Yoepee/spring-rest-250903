package com.back.global.rsData;

public record RsData<T>(
        String resultCode,
        String massage,
        T data
) {
    public RsData(String resultCode, String massage) {
        this(resultCode, massage, null);
    }
}
