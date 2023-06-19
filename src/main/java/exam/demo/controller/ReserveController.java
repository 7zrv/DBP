package exam.demo.controller;

import exam.demo.dto.MovieDto;
import exam.demo.dto.ReservationDto;
import exam.demo.dto.ReserveRequestDto;
import exam.demo.entity.*;
import exam.demo.repository.SeatRepository;
import exam.demo.service.*;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
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
    private final MovieService movieService;
    private final ScreenroomService screenroomService;
    private final MemberService memberService;

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


    @GetMapping("/reserveCheckPage/{theaterId}/{scheduleId}/{seatId}")
    public String showCheckPage(@PathVariable Long theaterId,
                                @PathVariable Long scheduleId,
                                @PathVariable Long seatId, Principal principal,
                                Model model){
        Theater theater = theaterService.getTheaterById(theaterId);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        Movie movie = movieService.getMovieById(schedule.getMovieId());
        Screenroom screenroom = screenroomService.getScreenRoomById(schedule.getScreenroomId());
        Seat seat = seatService.getSeatBySeatId(seatId);
        Member member = memberService.getMemberByUsername(principal.getName());


        seat.updateSeatStatus("UNAVAILABLE");
        seatService.updateSeatStatus(seat);

        model.addAttribute("theaterName", theater.getTheaterName());
        model.addAttribute("screenroomName", screenroom.getScreenroomName());
        model.addAttribute("movieTitle", movie.getTitle());
        model.addAttribute("movieTime", schedule.getStartTime());
        model.addAttribute("seatRow", seat.getSeatRowNumber());
        model.addAttribute("seatCol", seat.getSeatNumber());
        model.addAttribute("seat", seat);
        model.addAttribute("member", member);
        return "/reserveCheckPage";
    }



    @PostMapping("/cancelReservation")
    public String cancelReservation(@RequestParam("reservationId") Long reservationId) {
        reservationService.deleteReserveById(reservationId);
        return "redirect:/mypage";
    }



}
