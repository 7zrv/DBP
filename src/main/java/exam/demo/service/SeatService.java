package exam.demo.service;

import exam.demo.dto.SeatDto;
import exam.demo.entity.Seat;
import exam.demo.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;



    public List<Seat> createSeats(Long scheduleId, int numRows, int numSeatsPerRow) {
        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= numRows; row++) {
            for (int seatNum = 1; seatNum <= numSeatsPerRow; seatNum++) {
                SeatDto seatDto = new SeatDto();
                seatDto.setScheduleId(scheduleId);
                seatDto.setSeatNumber(seatNum);
                seatDto.setRowNumber(row);
                seatDto.setStatus("AVAILABLE");
                seatDto.setPrice(12000);

                Seat seat = new Seat(seatDto);
                seats.add(seat);
            }
        }

        return seatRepository.saveAll(seats);
    }

    public void updateSeatStatus(Seat seat){
        seatRepository.save(seat);
    }


    public void deleteSeatsByScheduleId(Long scheduleId) {
        seatRepository.deleteByScheduleId(scheduleId);
    }

    public int getTotalSeatsByScheduleId(Long scheduleId) {
        return seatRepository.countByScheduleId(scheduleId);
    }

    public int getAvailableSeatsByScheduleId(Long scheduleId) {
        return seatRepository.countByScheduleIdAndStatus(scheduleId, "AVAILABLE");
    }

    public List<Seat> getSeatsByScheduleId(Long scheduleId) {
        return seatRepository.findByScheduleId(scheduleId);
    }

    public Seat getSeatBySeatId(Long seatId){
        return seatRepository.findBySeatId(seatId);
    }
}

