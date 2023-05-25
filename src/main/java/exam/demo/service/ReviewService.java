package exam.demo.service;

import exam.demo.dto.ReviewDto;
import exam.demo.entity.Movie;
import exam.demo.entity.Review;
import exam.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void createReview(Review review) throws IOException {
        reviewRepository.save(review);
    }

    public void modifyReview(Review review, ReviewDto reviewDto) throws IOException {
        review.updateReview(reviewDto);
        reviewRepository.save(review);
    }

    public void deleteReview(Review review){
        reviewRepository.delete(review);
    }

    public Review getReviewById(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElse(null);
    }

    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
