package hu.ps.ss.onlineinvoice.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeCustomAdapter extends XmlAdapter<String, LocalDateTime> {

    /**
     * @param string String representation of a date
     * @return a Parsed ZonedDateTimeObject
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public LocalDateTime unmarshal(String string) throws DateTimeParseException {
        return parse(string);
    }

    /**
     * @param dateTime a datetime instance
     * @return String representation of a date formatted as ISO
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public String marshal(LocalDateTime dateTime) throws DateTimeParseException {
        return print(dateTime);
    }

    public static LocalDateTime parse(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String print(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
