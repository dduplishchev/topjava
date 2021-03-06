package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();

        Map<String, List<UserMeal>> userMealsGroupedByDate =
                mealList.stream().collect(Collectors.groupingBy(item -> item.getDateTime().toLocalDate().toString()));


        userMealsGroupedByDate.forEach((date,userMeals)->{
            int sumOfCaloriesPerDay = userMealsGroupedByDate.get(date).stream().mapToInt(item->item.getCalories()).sum();

            userMeals.forEach(item -> {
                if(TimeUtil.isBetween(item.getDateTime().toLocalTime(),startTime,endTime)) {
                    boolean isExceedPerDay = false;
                    if (sumOfCaloriesPerDay > caloriesPerDay) {
                        isExceedPerDay = true;
                    }

                    UserMealWithExceed userMealWIthExceed =
                            new UserMealWithExceed(item.getDateTime(), item.getDescription(), item.getCalories(), isExceedPerDay);
                    result.add(userMealWIthExceed);
                }
            });
        });

        // TODO return filtered list with correctly exceeded field
        return result;
    }
}
