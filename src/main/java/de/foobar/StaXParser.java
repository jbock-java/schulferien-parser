package de.foobar;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaXParser {

    private static final String CLASS = "class";
    private static final String TD = "td";
    private static final String TH = "th";

    public List<Datum> readConfig(String configFile) throws XMLStreamException, FileNotFoundException {
        List<Datum> result = new ArrayList<>();
        // First, create a new XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Setup a new eventReader
        InputStream in = new FileInputStream(configFile);
        XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
        // read the XML document
        String month = "";

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                // If we have an item element, we create a new item
                String elementName = startElement.getName().getLocalPart();
                Iterator<Attribute> attributes;
                switch (elementName) {
                    case TD:
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(CLASS) &&
                                    (attribute.getValue().contains("eos_tinymonth_cell_general_ferien")
                                            || attribute.getValue().contains("eos_tinymonth_cell_next_ferien"))) {
                                result.add(new Datum(month, eventReader.nextEvent().asCharacters().getData().trim()));
                            }
                        }
                        break;
                    case TH:
                        attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(CLASS)
                                    && attribute.getValue().equals("eos_tinymonthview_nav")) {
                                eventReader.nextEvent();
                                eventReader.nextEvent();
                                month = eventReader.nextEvent().asCharacters().getData().trim();
                            }
                        }
                        break;
                }
            }
        }
        return result;
    }

}