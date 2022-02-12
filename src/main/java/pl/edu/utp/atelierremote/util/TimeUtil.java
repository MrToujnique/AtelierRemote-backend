package pl.edu.utp.atelierremote.util;

import com.google.common.collect.Range;
import pl.edu.utp.atelierremote.model.dto.FreeReservation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TimeUtil {

    public static List<FreeReservation> parseRangeToIntervals(Range<LocalTime> range) {
        List<FreeReservation> intervals = new ArrayList<>();
        LocalTime start = range.lowerEndpoint();
        LocalTime end = range.upperEndpoint();
        int duration = 120;

        Stream.iterate(
                start,
                time -> time.plusMinutes(duration).isBefore(end) || time.plusMinutes(duration).equals(end),
                time -> time.plusMinutes(duration))
                .forEach(time -> intervals.add(new FreeReservation(time, time.plusMinutes(duration))));
        return intervals;
    }
}