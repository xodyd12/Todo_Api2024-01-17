package com.study.todoapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedEmailExcetion extends RuntimeException{


    public DuplicatedEmailExcetion(String message) {
        super(message);
    }
}
