package com.diegoparra.gamescanner.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ListUtilsTest {

    @Test
    public void map_emptyList_returnEmptyList() {
        List<String> emptyList = Collections.emptyList();

        List<String> mappedList = ListUtils.map(emptyList, s -> null);

        assertThat(emptyList).isEmpty();
        assertThat(mappedList).isEmpty();
        assertThat(mappedList).isEqualTo(emptyList);
    }

    @Test
    public void map_normalList_returnMappedList() {
        List<String> list = Arrays.asList("element 1", "element 2");
        List<String> mappedList = ListUtils.map(list, s -> "mapped " + s);
        assertThat(mappedList).isEqualTo(Arrays.asList("mapped element 1", "mapped element 2"));
    }

    @Test
    public void map_mappingNullCase_returnMappedListWithNullValue() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> mappedList = ListUtils.map(list, integer -> integer == 3 ? null : integer);
        assertThat(mappedList).isEqualTo(Arrays.asList(1,2,null,4));
    }

    @Test
    public void mapNotNull_simpleCaseNoNullMapping_returnMappedList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> mappedList = ListUtils.mapNotNull(list, integer -> integer);
        assertThat(mappedList).isEqualTo(Arrays.asList(1,2,3,4));
    }

    @Test
    public void mapNotNull_mappingTurnNull_returnMappedListWithoutNulls() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> mappedList = ListUtils.mapNotNull(list, integer -> integer == 3 ? null : integer);
        assertThat(mappedList).isEqualTo(Arrays.asList(1,2,4));
    }

    @Test
    public void mapNotNull_allValuesTurnNull_returnEmptyList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<Integer> mappedList = ListUtils.mapNotNull(list, integer -> null);
        assertThat(mappedList).isEqualTo(Collections.emptyList());
    }



    @Test
    public void find_emptyList_returnNull() {
        List<String> emptyList = Collections.emptyList();
        String result = ListUtils.find(emptyList, s -> true);
        assertThat(emptyList).isEmpty();
        assertThat(result).isNull();
    }

    @Test
    public void find_elementInList_returnElement() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Integer result = ListUtils.find(list, integer -> integer == 2);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void find_elementNotInList_returnNull() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Integer result = ListUtils.find(list, integer -> integer == 5);
        assertThat(result).isNull();
    }

    @Test
    public void joinToString_list_returnElements() {
        List<String> list = Arrays.asList("element1", "element2", "element3");
        String result = ListUtils.joinToString(list, s -> s, ", ");
        assertThat(result).isEqualTo("element1, element2, element3");
    }

    @Test
    public void joinToString_emptyList_returnEmptyString() {
        List<String> list = Collections.emptyList();
        String result = ListUtils.joinToString(list, s -> s, ", ");
        assertThat(result).isEqualTo("");
    }

}