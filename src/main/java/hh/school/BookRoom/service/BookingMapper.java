package hh.school.BookRoom.service;

import hh.school.BookRoom.dto.Booking;
import hh.school.BookRoom.dto.BookingCreationDto;
import hh.school.BookRoom.dto.BookingResponseDto;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingMapper {
    public Booking toBooking(@Nonnull BookingCreationDto bookingCreationDto) {
        Booking booking = new Booking();
        booking.setRoomName(bookingCreationDto.getRoomName());
        booking.setDate(LocalDate.parse(bookingCreationDto.getDate()));
        booking.setStartTime(LocalTime.parse(bookingCreationDto.getStartTime()));
        booking.setEndTime(LocalTime.parse(bookingCreationDto.getEndTime()));
        booking.setBookedBy(bookingCreationDto.getBookedBy());
        return booking;
    }

    public BookingResponseDto toResponseDto(@Nonnull Booking booking) {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setId(booking.getId().toString());
        bookingResponseDto.setRoomName(booking.getRoomName());
        bookingResponseDto.setDate(booking.getDate().toString());
        bookingResponseDto.setStartTime(booking.getStartTime().toString());
        bookingResponseDto.setEndTime(booking.getEndTime().toString());
        bookingResponseDto.setBookedBy(booking.getBookedBy());
        return bookingResponseDto;
    }
}
