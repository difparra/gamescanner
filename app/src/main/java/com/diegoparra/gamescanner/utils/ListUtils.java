package com.diegoparra.gamescanner.utils;



import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ListUtils {

    public static <T, R> List<R> map(@NonNull List<T> list, @NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(mapper);

        List<R> newList = new ArrayList<R>(list.size());
        for (T element : list) {
            newList.add(mapper.apply(element));
        }
        return newList;
    }

    public static <T> String joinToString(@NonNull List<T> list, @NonNull Function<T, String> print, @NonNull String separator) {
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

    public static <T> String joinToString(@NonNull List<T> list, @NonNull String separator) {
        return joinToString(list, Objects::toString, separator);
    }

}
