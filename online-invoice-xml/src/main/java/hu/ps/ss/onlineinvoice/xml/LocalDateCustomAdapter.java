package hu.ps.ss.onlineinvoice.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateCustomAdapter extends XmlAdapter<String, LocalDate> {

    /**
     * @param string String representation of a date
     * @return a Parsed ZonedDateTimeObject
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public LocalDate unmarshal(String string) throws DateTimeParseException {
        return parse(string);
    }

    /**
     * @param dateTime a datetime instance
     * @return String representation of a date formatted as ISO
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public String marshal(LocalDate dateTime) throws DateTimeParseException {
        return print(dateTime);
    }

    public static LocalDate parse(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
    }

    public static String print(LocalDate dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE);
    }
}