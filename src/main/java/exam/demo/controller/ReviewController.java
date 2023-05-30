package exam.demo.controller;

import exam.demo.dto.ReviewDto;
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

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping("/reviews")
    public String showAllReview(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "all-review";
    }
    @GetMapping("/{id}/reviews/new")
    public String showReviewForm(@PathVariable("id") Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);

        List<Review> reviews = reviewService.getReviewsByMovieId(id);
        model.addAttribute("reviews", reviews);

        model.addAttribute("review", new Review());
        return "new-review-form";
    }

    @PostMapping("/{id}/reviews/new")
    public String createReview(@PathVariable("id") Long movieId, ReviewDto reviewDto, Principal principal) throws IOException {
        Member member = memberService.getMemberByUsername(principal.getName());

        reviewDto.setUserName(member.getUsername());
        Review review = new Review(reviewDto);
        review.updateIds(member.getMemberId(), movieId);

        reviewService.createReview(review);
        return "redirect:/movies/" + movieId;
    }

    @GetMapping("/{id}/reviews/{reviewId}/modify")
    public String showModifyReviewForm(@PathVariable("id") Long id, @PathVariable("reviewId") Long reviewId, Model model) {
        Movie movie = movieService.getMovieById(id);
        Review review = reviewService.getReviewById(reviewId);

        model.addAttribute("movie", movie);
        model.addAttribute("review", review);
        return "modify_review_form";
    }

    @PostMapping("/{id}/reviews/{reviewId}/modify")
    public String modifyReview(@PathVariable("id") Long movieId, @PathVariable("reviewId") Long reviewId, ReviewDto reviewDto) throws IOException {
        Review review = reviewService.getReviewById(reviewId);
        reviewService.modifyReview(review, reviewDto);
        return "redirect:/movies/" + movieId;
    }

    @GetMapping("/{movieId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable("movieId") Long movieId, @PathVariable("reviewId") Long reviewId, Model model) {
        Review review = reviewService.getReviewById(reviewId);
        reviewService.deleteReview(review);
        return "redirect:/movies/" + movieId;
    }


}


