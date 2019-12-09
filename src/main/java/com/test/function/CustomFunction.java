package com.test.function;

import org.springframework.util.StringUtils;

import java.util.Optional;

public class CustomFunction {


    public String isEmpty( String val ){

        return Optional.ofNullable(val).filter( e -> !StringUtils.isEmpty( e)).orElse("-");

    }
}
