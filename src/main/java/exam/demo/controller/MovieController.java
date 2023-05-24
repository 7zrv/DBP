package exam.demo.controller;


import exam.demo.dto.MovieDto;
import exam.demo.entity.Movie;
import exam.demo.entity.Review;
import exam.demo.service.MovieService;
import exam.demo.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movies")
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
    public String showMovieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

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
