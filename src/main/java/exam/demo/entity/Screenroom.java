package exam.demo.entity;


import exam.demo.dto.ScreenRoomDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Screenroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long screenroomId;
    private Long theaterId;
    private String screenroomName;


    public Screenroom(ScreenRoomDto screenRoomDto){
        this.theaterId = screenRoomDto.getTheaterId();
        this.screenroomName = screenRoomDto.getScreenroomName();

    }

    public void updateScreenroom(ScreenRoomDto screenRoomDto){
        this.theaterId = screenRoomDto.getTheaterId();
        this.screenroomName = screenRoomDto.getScreenroomName();

    }


}
