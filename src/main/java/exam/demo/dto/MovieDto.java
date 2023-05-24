package exam.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private String title;
    private String director;
    private String cast;
    private String genre;
    private String runtime;
    private String rating;
    private String releaseDate;
    private String country;
    private String plot;
}
