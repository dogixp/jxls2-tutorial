package com.test.function;

import lombok.val;

import java.util.Optional;

public class CustomFunction {


    public String isEmpty( String val ){

        return Optional.ofNullable(val).orElse("-");

    }
}
