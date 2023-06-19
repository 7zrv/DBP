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
import java.util.Optional;

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
    private final MemberService memberService;

    public Reservation createReserve(ReservationDto reservationDto, Principal principal) throws IOException {

        Member member = memberService.getMemberByUsername(principal.getName());
        reservationDto.setMemberId(member.getMemberId());
        Reservation reservation = new Reservation(reservationDto);

        reservationRepository.save(reservation);

        return reservation;
    }

    public void deleteReserve(Reservation reservation){

        reservationRepository.delete(reservation);

    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }
}
