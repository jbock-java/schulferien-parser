package de.foobar;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    record DateRange(LocalDate von, LocalDate bis) {
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        StaXParser read = new StaXParser();
        List<Datum> result = read.readConfig("data/schulferien_2024.xml");
        LocalDate groupStart = null;
        LocalDate prev = null;
        List<DateRange> ranges = new ArrayList<>();
        for (Datum datum : result) {
            LocalDate date = datum.getDate();
            if (groupStart == null) {
                groupStart = date;
            }
            if (prev != null && !prev.plusDays(1).equals(date)) {
                ranges.add(new DateRange(groupStart, prev));
                groupStart = date;
            }
            prev = date;
        }
        ranges.add(new DateRange(groupStart, prev));
        for (DateRange range : ranges) {
            System.out.println("'" + range.von + "_" + range.bis + "',");
        }
    }
}