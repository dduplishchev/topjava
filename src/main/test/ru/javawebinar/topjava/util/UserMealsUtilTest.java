package ru.javawebinar.topjava.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

public class UserMealsUtilTest {
    @Test
    void testGetFilteredWithExceeded() {
        // prepare
        @SuppressWarnings("Duplicates")
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        List<UserMealWithExceed> expected = Arrays.asList(
                new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500,false),
                new UserMealWithExceed(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000,true)
        );

        LocalTime start = LocalTime.of(7, 0);
        LocalTime finish = LocalTime.of(12,0);
        int calories = 2000;

        // execute
        List<UserMealWithExceed> actual = UserMealsUtil.getFilteredWithExceeded(mealList, start, finish, calories);

        // validate
        assertArrayEquals(expected.toArray(),actual.toArray());
    }
}
