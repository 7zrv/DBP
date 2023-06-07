package exam.demo.service;

import exam.demo.dto.ScheduleDto;
import exam.demo.entity.Schedule;
import exam.demo.entity.Screenroom;
import exam.demo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScreenroomService screenRoomService;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Schedule ID: " + scheduleId));
    }

    public Schedule createSchedule(ScheduleDto scheduleDto) {

        Schedule schedule = new Schedule(scheduleDto);
        Screenroom screenRoom = screenRoomService.getScreenRoomById(scheduleDto.getScreenroomId());


        return scheduleRepository.save(schedule);
    }

    public Schedule updateScheduleTime(Long scheduleId, ScheduleDto scheduleDto) {
        Schedule schedule = getScheduleById(scheduleId);
        schedule.updateScheduleTime(scheduleDto);
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        scheduleRepository.delete(schedule);
    }

    public List<Schedule> getSchedulesByScreenRoom(Long screenRoomId) {
        return scheduleRepository.findByScreenroomId(screenRoomId);
    }




}
