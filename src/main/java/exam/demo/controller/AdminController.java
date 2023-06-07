package exam.demo.controller;

import exam.demo.dto.ScheduleDto;
import exam.demo.dto.ScreenRoomDto;
import exam.demo.dto.TheaterDto;

import exam.demo.entity.*;
import exam.demo.repository.TheaterRepository;
import exam.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Transactional
public class AdminController {

    private final TheaterRepository theaterRepository;
    private final TheaterService theaterService;
    private final MovieService movieService;
    private final ScreenroomService screenRoomService;
    private final ScheduleService scheduleService;

    private final SeatService seatService;

    @GetMapping("/adminPage")
    public String showAddTheaterForm(Model model) {
        model.addAttribute("theater", new Theater());

        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);

        List<Theater> theaters = theaterService.getAllTheater();
        model.addAttribute("theaters", theaters);

        List<Screenroom> screenrooms = screenRoomService.getAllScreenRooms();
        model.addAttribute("screenrooms", screenrooms);

        List<Schedule> schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);

        List<Integer> totalSeatsList = new ArrayList<>();
        List<Integer> availableSeatsList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            int totalSeats = seatService.getTotalSeatsByScheduleId(schedule.getScheduleId());
            int availableSeats = seatService.getAvailableSeatsByScheduleId(schedule.getScheduleId());
            totalSeatsList.add(totalSeats);
            availableSeatsList.add(availableSeats);
        }
        model.addAttribute("totalSeatsList", totalSeatsList);
        model.addAttribute("availableSeatsList", availableSeatsList);

        return "/adminPage";
    }
    //영화관 관련 컨트롤러들
    @PostMapping("/theater/new")
    public String addTheater(@ModelAttribute TheaterDto theaterDto) throws IOException {

        Theater theater = new Theater(theaterDto);
        theaterService.createTheater(theater);

        return "redirect:/admin/adminPage";
    }
    @PostMapping("/theater/addMovie")
    public String addMovieToTheater(@RequestParam Long theaterId, @RequestParam Long movieId) {
        theaterService.addMovieToTheater(theaterId, movieId);
        return "redirect:/admin/adminPage";
    }
    @GetMapping("/theater/{TheaterId}/edit")
    public String showTheaterEditForm(@PathVariable Long TheaterId, Model model) {
        Theater theater = theaterService.getTheaterById(TheaterId);
        model.addAttribute("theater", theater);
        model.addAttribute("theaterDto", new TheaterDto());

        List<Movie> movies = theaterService.getMoviesInScreen(theater);
        model.addAttribute("movies", movies);

        return "TheaterEditForm";
    }
    @PostMapping("/theater/update/{TheaterId}")
    public String updateTheater(@PathVariable Long TheaterId, @ModelAttribute TheaterDto theaterDto, @RequestParam(value = "deletedMovies", required = false) List<Long> deletedMovies) {
        Theater theater = theaterService.getTheaterById(TheaterId);
        if (theater != null) {
            theater.updateTheater(theaterDto);

            // 영화 삭제 처리
            if (deletedMovies != null && !deletedMovies.isEmpty()) {
                theaterService.deleteMoviesFromTheater(TheaterId, deletedMovies);
            }

            theaterService.updateTheater(theater);
        }
        return "redirect:/admin/adminPage";
    }
    @GetMapping("/theater/{TheaterId}/delete")
    public String deleteMovie(@PathVariable Long TheaterId, Model model) {
        Theater theater = theaterService.getTheaterById(TheaterId);
        theaterService.deleteTheater(theater);

        return "redirect:/admin/adminPage";
    }


    //상영관 컨트롤러들
    @PostMapping("/screenroom/new")
    public String addScreenRoom(@ModelAttribute ScreenRoomDto screenRoomDto) {
        Screenroom screenroom = new Screenroom(screenRoomDto);
        screenRoomService.createScreenRoom(screenroom);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/screenroom/{screenroomId}/edit")
    public String showScreenroomEditForm(@PathVariable Long screenroomId, Model model){
        Screenroom screenroom = screenRoomService.getScreenRoomById(screenroomId);
        model.addAttribute("screenroom", screenroom);
        List<Theater> theaters = theaterService.getAllTheater();
        model.addAttribute("theaters", theaters);

        return "ScreenroomEditForm";
    }

    @PostMapping("/update/{screenroomId}")
    public String editScreenroom(@PathVariable Long screenroomId, @ModelAttribute ScreenRoomDto screenRoomDto) {
        Screenroom screenRoom = screenRoomService.getScreenRoomById(screenroomId);
        screenRoomService.updateScreenRoom(screenRoom, screenRoomDto);

        return "redirect:/admin/adminPage";
    }


    @GetMapping ("/screenroom/{ScreenRoomId}/delete")
    public String deleteScreenroom(@PathVariable Long ScreenRoomId, Model model){
        Screenroom screenRoom = screenRoomService.getScreenRoomById(ScreenRoomId);
        screenRoomService.deleteScreenRoom(screenRoom);
        return "redirect:/admin/adminPage";
    }

    ///상영일정 컨트롤러들

    @PostMapping("/schedule/create")
    public String addSchedule(@ModelAttribute ScheduleDto scheduleDto){


        scheduleService.createSchedule(scheduleDto);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/schedule/{scheduleId}/edit")
    public String showScheduleEditForm(@PathVariable Long scheduleId, Model model){
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        model.addAttribute("schedule", schedule);
        List<Screenroom> screenrooms = screenRoomService.getAllScreenRooms();
        model.addAttribute("screenrooms", screenrooms);

        return "ScheduleEditForm";
    }

    @PostMapping("/schedule/{scheduleId}/edit")
    public String editSchedule(@PathVariable Long scheduleId, @ModelAttribute ScheduleDto scheduleDto) {

        scheduleService.updateScheduleTime(scheduleId, scheduleDto);
        return "redirect:/admin/adminPage";
    }

    @GetMapping ("/schedule/{scheduleId}/delete")
    public String deleteSchedule(@PathVariable Long scheduleId){

        scheduleService.deleteSchedule(scheduleId);
        return "redirect:/admin/adminPage";
    }

    ///좌석 컨트롤러
    @PostMapping("/seats/create/")
    public String createSeats(@RequestParam Long scheduleId, @RequestParam int numRows, @RequestParam int numSeatsPerRow) {
        seatService.createSeats(scheduleId, numRows, numSeatsPerRow);

        return "redirect:/admin/adminPage";
    }
    @PostMapping("/seats/delete/")
    public String deleteSeats(@RequestParam Long scheduleId) {
        seatService.deleteSeatsByScheduleId(scheduleId);

        return "redirect:/admin/adminPage";
    }




}
