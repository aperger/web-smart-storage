package hu.ps.ss.onlineinvoice.xml;


import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeCustomAdapter extends XmlAdapter<String, ZonedDateTime> {

    /**
     * @param string String representation of a date
     * @return a Parsed ZonedDateTimeObject
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public ZonedDateTime unmarshal(String string) throws DateTimeParseException {
        return parse(string);
    }

    /**
     * @param dateTime a datetime instance
     * @return String representation of a date formatted as ISO
     * @throws DateTimeParseException it will throw  DateTimeParseException exception if unable to parse
     */
    @Override
    public String marshal(ZonedDateTime dateTime) throws DateTimeParseException {
        return print(dateTime);
    }

    public static ZonedDateTime parse(String string) {
        return ZonedDateTime.parse(string, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String print(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
