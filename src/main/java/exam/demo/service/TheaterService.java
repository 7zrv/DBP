package exam.demo.service;



import exam.demo.dto.TheaterDto;
import exam.demo.entity.Movie;
import exam.demo.entity.Theater;
import exam.demo.repository.MovieRepository;
import exam.demo.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final MovieRepository movieRepository;

    public void createTheater(Theater theater) throws IOException {
        theaterRepository.save(theater);
    }

    public void updateTheater(Theater theater) {
        theaterRepository.save(theater);
    }
    public void deleteTheater(Theater theater){
        theaterRepository.delete(theater);
    }

    public List<Theater> getAllTheater() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id).orElse(null);
    }

    public List<Movie> getMoviesInScreen(Theater theater) {
        List<Long> screenMovieIds = theater.getScreenMovies();
        List<Movie> movies = new ArrayList<>();
        for (Long movieId : screenMovieIds) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid movieId: " + movieId));
            movies.add(movie);
        }
        return movies;
    }


    public void addMovieToTheater(Long theaterId, Long movieId) {
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new IllegalArgumentException("Invalid theaterId: " + theaterId));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Invalid movieId: " + movieId));

        List<Long> screenMovies = new ArrayList<>(theater.getScreenMovies());
        if (!screenMovies.contains(movieId)) {
            screenMovies.add(movieId);
            theater.updateScreenMovies(screenMovies);
            theaterRepository.save(theater);
        }
    }

    public void deleteMoviesFromTheater(Long theaterId, List<Long> movieIds) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid theaterId: " + theaterId));

        List<Long> screenMovies = new ArrayList<>(theater.getScreenMovies());
        screenMovies.removeAll(movieIds);
        theater.updateScreenMovies(screenMovies);
        theaterRepository.save(theater);
    }

    public List<Theater> getTheatersByMovieId(Long movieId) {
        List<Theater> theaters = theaterRepository.findAll();
        List<Theater> movieTheaters = new ArrayList<>();
        for (Theater theater : theaters) {
            List<Long> screenMovies = theater.getScreenMovies();
            if (screenMovies.contains(movieId)) {
                movieTheaters.add(theater);
            }
        }
        return movieTheaters;
    }


}
