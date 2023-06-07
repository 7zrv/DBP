package exam.demo.repository;

import exam.demo.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {


    List<Seat> findByScheduleId(Long scheduleId);
    void deleteByScheduleId(Long scheduleId);

    int countByScheduleId(Long scheduleId);

    int countByScheduleIdAndStatus(Long scheduleId, String available);

    Seat findBySeatId(Long seatId);
}
