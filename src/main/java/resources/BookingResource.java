package resources;

import hh.school.BookRoom.dao.InMemoryBookingDao;
import hh.school.BookRoom.dto.BookingCreationDto;
import hh.school.BookRoom.dto.BookingResponseDto;
import hh.school.BookRoom.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {
    private static final BookingService bookingService = new BookingService(new InMemoryBookingDao());

    @GET
    public List<BookingResponseDto> getBookingsList(@QueryParam("roomName") String roomName,
                                                    @QueryParam("date")
                                                    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "date must be in format yyyy-MM-dd")
                                                    String date) {
        return bookingService.getBookings(roomName, date);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bookRoom(@Valid BookingCreationDto bookingCreationDto) {
        BookingResponseDto booking = bookingService.create(bookingCreationDto);
        return Response
                .status(Response.Status.CREATED)
                .entity(booking)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getBookingById(@PathParam("id") long id) {
        BookingResponseDto booking = bookingService.get(id);
        if (booking == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Booking not found: " + id)
                    .build();
        }
        return Response
                .ok(booking)
                .build();
    }
}
