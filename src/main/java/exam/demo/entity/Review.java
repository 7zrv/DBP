package exam.demo.entity;

import exam.demo.dto.ReviewDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long review_id;
    @Column
    private Long movieId;
    @Column
    private Long memberId;
    @Column
    private Integer rating;
    @Column
    private String content;

    public Review(ReviewDto reviewDto){
        this.rating = reviewDto.getRating();
        this.content = reviewDto.getContent();
    }

    public void updateReview(ReviewDto reviewDto){
        this.rating = reviewDto.getRating();
        this.content = reviewDto.getContent();
    }

    public void updateIds(Long memberId, Long movieId){
        this.memberId = memberId;
        this.movieId = movieId;
    }

}