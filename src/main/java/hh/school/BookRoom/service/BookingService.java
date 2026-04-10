package hh.school.BookRoom.service;

import hh.school.BookRoom.dao.BookingDao;
import hh.school.BookRoom.dto.Booking;
import hh.school.BookRoom.dto.BookingCreationDto;
import hh.school.BookRoom.dto.BookingResponseDto;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private final BookingDao bookingDao;
    private final BookingMapper bookingMapper;
    private final BookingValidator bookingValidator;

    public BookingService(BookingDao bookingDao) {
        this(bookingDao, new BookingMapper(), new BookingValidator(bookingDao));
    }

    public BookingService(BookingDao bookingDao, BookingMapper bookingMapper, BookingValidator bookingValidator) {
        this.bookingDao = bookingDao;
        this.bookingMapper = bookingMapper;
        this.bookingValidator = bookingValidator;
    }

    public BookingResponseDto create(@Nonnull BookingCreationDto bookingCreationDto) {
        Booking booking = bookingMapper.toBooking(bookingCreationDto);
        bookingValidator.validate(booking);
        return bookingMapper.toResponseDto(bookingDao.add(booking));
    }

    public BookingResponseDto get(Long id) {
        Booking booking = bookingDao.get(id);
        if (booking == null) {
            return null;
        }

        return bookingMapper.toResponseDto(booking);
    }

    public List<BookingResponseDto> getBookings(String roomName, String date) {
        List<Booking> bookings = (roomName == null || roomName.isBlank()) && (date == null || date.isBlank())
                ? bookingDao.getAll()
                : bookingDao.getByRoomAndDate(roomName, (date == null || date.isBlank()) ? null : LocalDate.parse(date));

        return bookings.stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }
}
