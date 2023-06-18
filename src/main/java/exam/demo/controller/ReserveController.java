package exam.demo.controller;

import exam.demo.dto.ReserveRequestDto;
import exam.demo.entity.*;
import exam.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class ReserveController {

    private final TheaterService theaterService;
    private final ScreenroomService screenRoomService;
    private final ScheduleService scheduleService;
    private final ReservationService reservationService;

    private final SeatService seatService;

    @GetMapping("/reserve/{movieId}")
    public String showReservationPage(@PathVariable Long movieId, Model model) {
        List<Theater> movieTheaters = theaterService.getTheatersByMovieId(movieId);
        List<Screenroom> screenrooms = screenRoomService.getAllScreenRooms();
        List<Schedule> schedules = scheduleService.getAllSchedules();

        model.addAttribute("movieId", movieId);
        model.addAttribute("movieTheaters", movieTheaters);
        model.addAttribute("screenrooms", screenrooms);
        model.addAttribute("schedules", schedules);

        return "reserve";
    }

    @GetMapping("/admin/seats/{scheduleId}")
    @ResponseBody
    public List<Seat> getSeatsByScheduleId(@PathVariable Long scheduleId) {
        return seatService.getSeatsByScheduleId(scheduleId);
    }



    @PostMapping("/reserve/check")
    public String checkReservation(@ModelAttribute ReserveRequestDto reserveRequestDto, Principal principal, Model model) throws IOException {

        Reservation reservation = reservationService.createReserve(reserveRequestDto, principal);
        Long reservationId = reservation.getReserveId();
        return "redirect:/movies/reserveCheckPage/" + reservationId; // 예매 성공 페이지로 리다이렉트
    }

    @GetMapping("/reserveCheckPage/{reserveId}")
    public String showCheckPage(@PathVariable Long reserveId, Model model){
        Reservation reservation = reservationService.getReservationById(reserveId);
        model.addAttribute("reservation", reservation);

        return "/reserveCheckPage";
    }
}
