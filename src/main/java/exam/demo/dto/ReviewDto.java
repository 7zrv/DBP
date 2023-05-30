package exam.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


@Getter
@Setter
public class ReviewDto {

    private Long movieId;
    private Long memberId;

    private String userName;
    private Integer rating;
    private String content;
}
