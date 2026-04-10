package hh.school.BookRoom.dao;

import hh.school.BookRoom.dto.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {
    Booking add(Booking booking);
    Booking get(Long id);
    List<Booking> getAll();
    List<Booking> getByRoomAndDate(String roomName, LocalDate date);
}
