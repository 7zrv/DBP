package exam.demo.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class ReserveRequestDto {
    private Long theaterId;
    private Long scheduleId;
    private Long seatId;
}
