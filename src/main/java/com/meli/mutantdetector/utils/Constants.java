package com.meli.mutantdetector.utils;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern MUTANT_PATTERN = Pattern.compile("([ACGT])\\1{3}", Pattern.CASE_INSENSITIVE);
}
