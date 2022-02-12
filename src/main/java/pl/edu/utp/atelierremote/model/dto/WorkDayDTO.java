package pl.edu.utp.atelierremote.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.utp.atelierremote.model.WorkDay;


import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class WorkDayDTO{
    private Long id;
    private DayOfWeek weekDay;
    private LocalTime startTime;
    private LocalTime endTime;

    public WorkDayDTO(WorkDay workDay)
    {
        this.id = workDay.getId();
        this.weekDay = workDay.getWeekDay();
        this.startTime = workDay.getStartTime();
        this.endTime = workDay.getEndTime();
    }

}
