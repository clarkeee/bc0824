package database;

import model.Holiday;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class HolidayDatabase {
    private Map<LocalDate, Holiday> holidayDatabase;

    public HolidayDatabase() {
        holidayDatabase = new HashMap<>();
    }

    public void saveHoliday(Holiday holiday) {
        holidayDatabase.put(holiday.getDate(), holiday);
    }

    public Holiday getHoliday(LocalDate date) {
        return holidayDatabase.get(date);
    }

    public void listHolidays() {
        for (Holiday holiday : holidayDatabase.values()) {
            System.out.println(holiday);
        }
    }
}
