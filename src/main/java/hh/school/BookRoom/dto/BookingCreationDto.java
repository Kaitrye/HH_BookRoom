package hh.school.BookRoom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreationDto {
    @NotBlank
    private String roomName;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "date must be in format yyyy-MM-dd")
    private String date;

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "startTime must be in format HH:mm")
    private String startTime;

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}", message = "endTime must be in format HH:mm")
    private String endTime;

    @NotBlank
    private String bookedBy;
}
