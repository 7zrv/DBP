package exam.demo.service;


import exam.demo.dto.ReservationDto;
import exam.demo.dto.ReserveRequestDto;
import exam.demo.entity.*;
import exam.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleService scheduleService;
    private final TheaterService theaterService;
    private final SeatService seatService;
    private final ScreenroomService screenroomService;
    private final MovieService movieService;
    public void createReserve(ReserveRequestDto reserveRequestDto, Principal principal) throws IOException {

        Schedule schedule = scheduleService.getScheduleById(reserveRequestDto.getScheduleId());
        Theater theater = theaterService.getTheaterById(reserveRequestDto.getTheaterId());
        Seat seat = seatService.getSeatBySeatId(reserveRequestDto.getSeatId());
        Screenroom screenroom = screenroomService.getScreenRoomById(schedule.getScreenroomId());
        Movie movie = movieService.getMovieById(schedule.getMovieId());

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTheaterName(theater.getTheaterName());
        reservationDto.setScreenroomName(screenroom.getScreenroomName());
        reservationDto.setMovieName(movie.getTitle());
        reservationDto.setStartTime(schedule.getStartTime());
        reservationDto.setMemberId(principal.getName());

        Reservation reservation = new Reservation(reservationDto);

        reservationRepository.save(reservation);
    }
}
