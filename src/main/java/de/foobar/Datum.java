package de.foobar;

import java.time.LocalDate;
import java.time.Month;

public record Datum(String month, String day) {

    LocalDate getDate() {
        String[] tokens = month.split("\\s+");
        int year = Integer.parseInt(tokens[1]);
        int dayOfMonth = Integer.parseInt(day);
        return LocalDate.of(year, getMonth(tokens[0]), dayOfMonth);
    }

    private static Month getMonth(String m) {
        return switch (m) {
            case "Januar" -> Month.JANUARY;
            case "Februar" -> Month.FEBRUARY;
            case "März" -> Month.MARCH;
            case "April" -> Month.APRIL;
            case "Mai" -> Month.MAY;
            case "Juni" -> Month.JUNE;
            case "Juli" -> Month.JULY;
            case "August" -> Month.AUGUST;
            case "September" -> Month.SEPTEMBER;
            case "Oktober" -> Month.OCTOBER;
            case "November" -> Month.NOVEMBER;
            case "Dezember" -> Month.DECEMBER;
            default -> throw new IllegalArgumentException(m);
        };
    }
}
