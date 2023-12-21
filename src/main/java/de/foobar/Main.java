package de.foobar;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        StaXParser read = new StaXParser();
        List<Datum> result = read.readConfig("data/schulferien_2024.xml");
        for (Datum datum : result) {
            System.out.println(datum.getDate());
        }
    }
}