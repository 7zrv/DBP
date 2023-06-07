package exam.demo.entity;

import exam.demo.dto.TheaterDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long theaterId;

    private String TheaterName;
    private String TheaterLocal;

    @ElementCollection
    private List<Long> screenMovies;

    public Theater(TheaterDto theaterDto) {
        this.TheaterName = theaterDto.getTheaterName();
        this.TheaterLocal = theaterDto.getTheaterLocal();
    }

    public void updateTheater(TheaterDto theaterDto){
        this.TheaterName = theaterDto.getTheaterName();
        this.TheaterLocal = theaterDto.getTheaterLocal();
    }

    public void updateScreenMovies(List<Long> movieIds) {
        this.screenMovies = movieIds;
    }

}
