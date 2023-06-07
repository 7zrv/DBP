package exam.demo.entity;

import exam.demo.dto.ScheduleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    private Long screenroomId;
    private Long movieId;
    private String startTime;
    private String endTime;




    public Schedule(ScheduleDto scheduleDto){
        this.screenroomId = scheduleDto.getScreenroomId();
        this.movieId = scheduleDto.getMovieId();
        this.startTime = scheduleDto.getStartTime();
        this.endTime = scheduleDto.getEndTime();
    }

    public void updateScheduleTime(ScheduleDto scheduleDto){
        this.startTime = scheduleDto.getStartTime();
        this.endTime = scheduleDto.getEndTime();
    }

}