package exam.demo.service;

import exam.demo.dto.ScreenRoomDto;
import exam.demo.entity.Screenroom;
import exam.demo.repository.ScreenroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenroomService {
    private final ScreenroomRepository screenRoomRepository;


    public List<Screenroom> getAllScreenRooms() {
        return screenRoomRepository.findAll();
    }

    public Screenroom getScreenRoomById(Long screenRoomId) {
        return screenRoomRepository.findById(screenRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Screen Room ID: " + screenRoomId));
    }

    public Screenroom createScreenRoom(Screenroom screenRoom) {
        return screenRoomRepository.save(screenRoom);
    }

    public void updateScreenRoom(Screenroom screenRoom, ScreenRoomDto screenRoomDto) {
        screenRoom.updateScreenroom(screenRoomDto);
        screenRoomRepository.save(screenRoom);
    }

    public void deleteScreenRoom(Screenroom screenRoom) {
        screenRoomRepository.delete(screenRoom);
    }

    public List<Screenroom> getScreensByTheater(Long theaterId) {
        return screenRoomRepository.findByTheaterId(theaterId);
    }
}
