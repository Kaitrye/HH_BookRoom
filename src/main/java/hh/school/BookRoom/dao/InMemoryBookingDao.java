package hh.school.BookRoom.dao;

import hh.school.BookRoom.dto.Booking;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class InMemoryBookingDao implements BookingDao {
    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Booking add(@Nonnull Booking booking) {
        booking.setId(idGenerator.getAndIncrement());
        bookings.add(booking);
        return booking;
    }

    @Override
    public Booking get(Long id) {
        return bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Booking> getAll() {
        return List.copyOf(bookings);
    }

    @Override
    public List<Booking> getByRoomAndDate(String roomName, LocalDate date) {
        return filterByDate(filterByRoomName(bookings.stream(), roomName), date)
                .toList();
    }

    private Stream<Booking> filterByRoomName(Stream<Booking> bookingsStream, String roomName) {
        if (roomName == null) {
            return bookingsStream;
        }

        return bookingsStream.filter(booking -> booking.getRoomName().equals(roomName));
    }

    private Stream<Booking> filterByDate(Stream<Booking> bookingsStream, LocalDate date) {
        if (date == null) {
            return bookingsStream;
        }

        return bookingsStream.filter(booking -> booking.getDate().equals(date));
    }
}
