package com.diegoparra.gamescanner.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ListUtils {

    public static <T, R> List<R> map(@NotNull List<T> list, @NotNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(mapper);

        List<R> newList = new ArrayList<R>(list.size());
        for (T element : list) {
            newList.add(mapper.apply(element));
        }
        return newList;
    }

    public static <T> String joinToString(@NotNull List<T> list, @NotNull Function<T, String> print, @NotNull String separator) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(print);
        Objects.requireNonNull(separator);

        StringBuilder str = new StringBuilder();
        for (T element : list) {
            String printStr = print.apply(element);
            str.append(printStr).append(separator);
        }
        return str.toString();
    }

    public static <T> String joinToString(@NotNull List<T> list, @NotNull String separator) {
        return joinToString(list, Objects::toString, separator);
    }

}
