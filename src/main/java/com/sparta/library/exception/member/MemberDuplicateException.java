package com.sparta.library.exception.member;

public class MemberDuplicateException extends RuntimeException {
    public MemberDuplicateException(String message) {
        super(message);
    }
}
