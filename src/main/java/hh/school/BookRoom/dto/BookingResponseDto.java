package hh.school.BookRoom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDto {
    private String id;
    private String roomName;
    private String date;
    private String startTime;
    private String endTime;
    private String bookedBy;
}
