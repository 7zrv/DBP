package exam.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private String theaterName;
    private String screenroomName;
    private String movieName;
    private String startTime;

    private String memberId;
    private String seatInfo;
}
