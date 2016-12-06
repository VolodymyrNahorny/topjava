package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
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
        List<UserMealWithExceed> filteredMealList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed u:filteredMealList) {
            System.out.println(u.getDescription() + " " + u.getDateTime() + " " + u.getCalories() + " " + u.isExceed());
        }
//        .toLocalDate();
//        .toLocalTime();
    }


    /**
     * Реализовать метод UserMealsUtil.getFilteredWithExceeded:
     -  должны возвращаться только записи между startTime и endTime
     -  поле UserMealWithExceed.exceed должно показывать,
     превышает ли сумма калорий за весь день параметра метода caloriesPerDay

     Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.

     - Проверте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
     - Оцените Time complexity вашего алгоритма, если он O(N*N)- попробуйте сделать O(N).
     */
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> filteredMealList = new ArrayList<>();
        // подсчет суммы калорий за период между startTime и endTime
        Map<LocalDate, Integer> totalCaloriesPerDate = new HashMap<>();
        // подсчет суммы калорий за каждый день (ключ: дата, значение: калории)
        mealList.forEach(m -> totalCaloriesPerDate.put(m.getDateTime().toLocalDate(), totalCaloriesPerDate.containsKey(m.getDateTime().toLocalDate()) ? totalCaloriesPerDate.get(m.getDateTime().toLocalDate()) + m.getCalories() : m.getCalories()));
//        mealList
//                .stream()
//                .forEach(m -> totalCaloriesPerDate.put(m.getDateTime().toLocalDate(), totalCaloriesPerDate.containsKey(m.getDateTime().toLocalDate()) ? totalCaloriesPerDate.get(m.getDateTime().toLocalDate()) + m.getCalories() : m.getCalories()));
        mealList
                .stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(m -> filteredMealList.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), totalCaloriesPerDate.get(m.getDateTime().toLocalDate())> caloriesPerDay)));
        return filteredMealList;
    }
}
