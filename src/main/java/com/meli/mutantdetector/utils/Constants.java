package com.meli.mutantdetector.utils;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern MUTANT_PATTERN = Pattern.compile("([ACGT])\\1{3}", Pattern.CASE_INSENSITIVE);
    public static final String ERROR_DNA_BAD_STRUCTURE_MESSAGE = "DNA doesn't have right structure";
    public static final String ERROR_DNA_EMPTY_MESSAGE = "DNA is empty";
    public static final String ERROR_CHARACTERS_NOT_ALLOW_MESSAGE =  "DNA only allows the letters A,T,C,G";
}
