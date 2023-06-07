package exam.demo.entity;

import exam.demo.dto.ReservationDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;
    private String theaterName;
    private String screenroomName;
    private String movieName;
    private String startTime;
    private String memberId;
    private String seatInfo;


    public Reservation(ReservationDto reservationDto){
        this.theaterName = reservationDto.getTheaterName();
        this.screenroomName = reservationDto.getScreenroomName();
        this.movieName = reservationDto.getMovieName();
        this.startTime = reservationDto.getStartTime();
        this.memberId = reservationDto.getMemberId();
        this.seatInfo = reservationDto.getSeatInfo();
    }

}
