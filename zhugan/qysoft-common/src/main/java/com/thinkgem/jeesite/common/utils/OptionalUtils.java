package com.thinkgem.jeesite.common.utils;

import java.util.Optional;

/**
 * Created by yankai on 2017/6/29.
 */
public class OptionalUtils {
    public static Optional<Integer> strToInt(String str) {
        if (str == null || str.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            Integer value = Integer.parseInt(str);
            return Optional.of(value);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    public static Optional<Long> strToLong(String str) {
        if (str == null || str.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            Long value = Long.parseLong(str);
            return Optional.of(value);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    public static Optional<Double> strToDouble(String str) {
        if (str == null || str.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            Double value = Double.parseDouble(str);
            return Optional.of(value);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
