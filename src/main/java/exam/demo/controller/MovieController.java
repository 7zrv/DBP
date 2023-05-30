package exam.demo.controller;

import exam.demo.dto.MovieDto;
import exam.demo.entity.Member;
import exam.demo.entity.Movie;
import exam.demo.entity.Review;
import exam.demo.service.MemberService;
import exam.demo.service.MovieService;
import exam.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping( value = {"/", "/movies"})
    public String showAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);

        return "movies";
    }

    @GetMapping("/movies/register")
    public String showMovieRegistrationForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "registerMovie";
    }

    @PostMapping("/movies/register")
    public String registerMovie(@ModelAttribute MovieDto movieDto, MultipartFile imgFile) throws IOException {
        Movie movie = new Movie(movieDto);
        movieService.registerMovie(movie, imgFile);

        return "redirect:/movies";
    }

    @GetMapping("/movies/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model, Principal principal){
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        List<Review> reviews = reviewService.getReviewsByMovieId(id);
        model.addAttribute("reviews", reviews);



        String loggedInUserId = null;

        if (principal != null) {
            Member member = memberService.getMemberByUsername(principal.getName());
            loggedInUserId = member.getMemberId().toString();
        }
        model.addAttribute("loggedInUserId", loggedInUserId);
        //요청사항 memberId로 UserName 불러오기
        model.addAttribute("memberService", memberService);

        return "movieDetails";
    }

    @GetMapping("/movies/{id}/modify")
    public String showModifyMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "modifyMovie";
    }

    @PostMapping("/movies/{id}/modify")
    public String modifyMovie(@ModelAttribute MovieDto movieDto, MultipartFile imgFile, @PathVariable("id") Long id) throws IOException {
        Movie movie = movieService.getMovieById(id);
        movieService.modifyMovie(movie, movieDto, imgFile);
        return "redirect:/movies";
    }

    @GetMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        movieService.deleteMovie(movie);
        return "redirect:/movies";
    }

}
