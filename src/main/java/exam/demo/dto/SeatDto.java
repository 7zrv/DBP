package exam.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDto {
    private Long scheduleId;
    private int seatNumber;
    private int rowNumber;
    private String status;
    private int price;


}
