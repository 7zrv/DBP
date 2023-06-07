package exam.demo.entity;

import exam.demo.dto.SeatDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seatId;
    private Long scheduleId;
    private int seatNumber;
    private int seatRowNumber;
    private String status;
    private int price;

    public Seat(SeatDto seatDto) {
        this.scheduleId = seatDto.getScheduleId();
        this.seatNumber = seatDto.getSeatNumber();
        this.seatRowNumber = seatDto.getRowNumber();
        this.status = seatDto.getStatus();
        this.price = seatDto.getPrice();
    }

    public void updateSeatStatus(String seatStatus)
    {
        this.status = seatStatus;
    }

}
