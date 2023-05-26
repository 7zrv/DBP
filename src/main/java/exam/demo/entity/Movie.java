package exam.demo.entity;

import exam.demo.dto.MovieDto;
import lombok.*;
import javax.persistence.*;


@NoArgsConstructor
@Getter
@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movie_id;
    private String title;
    private String director;
    private String cast;
    private String genre;
    private String runtime;
    private String rating;
    private String releaseDate;
    private String country;
    private String plot;
    private String ImgPath;
    private String ImgName;

    public void uploadImage(String ImgPath, String ImgName) {
        this.ImgPath = ImgPath;
        this.ImgName = ImgName;
    }

    public Movie(MovieDto movieDto){
        this.title = movieDto.getTitle();
        this.director = movieDto.getDirector();
        this.cast = movieDto.getCast();
        this.genre = movieDto.getGenre();
        this.runtime = movieDto.getRuntime();
        this.rating = movieDto.getRating();
        this.releaseDate = movieDto.getReleaseDate();
        this.country = movieDto.getCountry();
        this.plot = movieDto.getPlot();
    }

    public void updateMovie(MovieDto movieDto){
        this.title = movieDto.getTitle();
        this.director = movieDto.getDirector();
        this.cast = movieDto.getCast();
        this.genre = movieDto.getGenre();
        this.runtime = movieDto.getRuntime();
        this.rating = movieDto.getRating();
        this.releaseDate = movieDto.getReleaseDate();
        this.country = movieDto.getCountry();
        this.plot = movieDto.getPlot();
    }

}