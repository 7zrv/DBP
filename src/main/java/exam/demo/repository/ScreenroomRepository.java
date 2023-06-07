package exam.demo.repository;

import exam.demo.entity.Screenroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenroomRepository extends JpaRepository<Screenroom, Long> {
    List<Screenroom> findByTheaterId(Long theaterId);
}
