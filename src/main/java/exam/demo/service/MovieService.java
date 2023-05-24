package exam.demo.service;

import exam.demo.dto.MovieDto;
import exam.demo.entity.Movie;
import exam.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void registerMovie(Movie movie, MultipartFile imgFile) throws IOException {
        uploadImageFile(movie, imgFile);
        movieRepository.save(movie);
    }

    public void modifyMovie(Movie movie, MovieDto movieDto, MultipartFile imgFile) throws IOException {
        uploadImageFile(movie, imgFile);
        movie.updateMovie(movieDto);
    }

    public void deleteMovie(Movie movie){
        movieRepository.delete(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void uploadImageFile(Movie movie, MultipartFile imgFile) throws IOException {
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String imgPath = "";

        //본인의 이미지 파일 업로드 경로를 입력해주세요.
        String projectPath = "/Users/yunseojin/projectImgFolder/";

        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + "_" + oriImgName;
        imgName = savedFileName;
        imgPath = "/files/" + projectPath + savedFileName;
        File saveFile = new File(projectPath, savedFileName);

        imgFile.transferTo(saveFile);
        movie.uploadImage(imgPath, imgName);
    }

}


