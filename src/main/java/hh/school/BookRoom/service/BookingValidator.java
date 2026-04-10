package hh.school.BookRoom.service;

import hh.school.BookRoom.dao.BookingDao;
import hh.school.BookRoom.dto.Booking;
import jakarta.annotation.Nonnull;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.time.LocalTime;

public class BookingValidator {
    private static final LocalTime WORKING_HOURS_START = LocalTime.of(8, 0);
    private static final LocalTime WORKING_HOURS_END = LocalTime.of(20, 0);

    private final BookingDao bookingDao;

    public BookingValidator(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    public void validate(@Nonnull Booking booking) {
        validateWorkingHours(booking);
        validateMinimumDuration(booking);
        validateNoOverlapping(booking);
    }

    private void validateNoOverlapping(@Nonnull Booking newBooking) {
        Booking conflictingBooking = bookingDao.getByRoomAndDate(newBooking.getRoomName(), newBooking.getDate()).stream()
                .filter(existingBooking -> isOverlapping(newBooking, existingBooking))
                .findFirst()
                .orElse(null);

        if (conflictingBooking != null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity("Booking time overlaps with booking id=" + conflictingBooking.getId())
                            .build()
            );
        }
    }

    private void validateWorkingHours(@Nonnull Booking booking) {
        if (!booking.getStartTime().isBefore(booking.getEndTime())) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("endTime must be later than startTime")
                            .build()
            );
        }

        if (booking.getStartTime().isBefore(WORKING_HOURS_START) || booking.getEndTime().isAfter(WORKING_HOURS_END)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Booking time must be within working hours 08:00-20:00")
                            .build()
            );
        }
    }

    private void validateMinimumDuration(@Nonnull Booking booking) {
        if (Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes() < 30) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Booking duration must be at least 30 minutes")
                            .build()
            );
        }
    }

    private boolean isOverlapping(@Nonnull Booking firstBooking, @Nonnull Booking secondBooking) {
        return firstBooking.getStartTime().isBefore(secondBooking.getEndTime())
                && firstBooking.getEndTime().isAfter(secondBooking.getStartTime());
    }
}
