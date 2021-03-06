package com.marcosbarbero.booking.web.resources;

import com.marcosbarbero.booking.dto.BookingDTO;
import com.marcosbarbero.booking.dto.converter.BookingConverter;
import com.marcosbarbero.booking.model.entity.Booking;
import com.marcosbarbero.booking.model.entity.enums.BookingStatus;
import com.marcosbarbero.booking.model.repository.BookingRepository;
import com.marcosbarbero.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Controller to handle requests to ${@link com.marcosbarbero.booking.model.entity.Booking} resource.
 *
 * @author Marcos Barbero
 */
@RestController
@RequestMapping(BookingController.URI)
public class BookingController extends BasicController<Booking, BookingRepository> {

    protected static final String URI = "";

    private final BookingService bookingService;

    @Autowired
    public BookingController(final BookingRepository bookingRepository, final BookingService bookingService) {
        super(URI, bookingRepository);
        this.bookingService = bookingService;
    }

    /**
     * Get the bookings for given customer id.
     *
     * @param id The customerId
     * @return A List of booking
     */
    @RequestMapping(method = GET, value = "/customer/{id}")
    public ResponseEntity<Collection<Booking>> getByCustomerId(@PathVariable Integer id) {
        Collection<Booking> bookings = this.bookingService.findByCustomerId(id);
        return ResponseEntity.ok(bookings);
    }


    /**
     * Return a ${@link Booking} for the given id.
     *
     * @param id The booking id
     * @return Status code 200 with Booking on response body
     */
    @RequestMapping(method = GET, value = "/{id}")
    public ResponseEntity<Booking> get(@PathVariable Integer id) {
        return super.get(id);
    }

    /**
     * Persist a ${@link Booking}.
     *
     * @param bookingDTO The Booking to persist
     * @param result     BindingResult
     * @param builder    UriComponentsBuilder
     * @return StatusCode 200 with the header Location with new resource
     */
    @RequestMapping(method = POST)
    public ResponseEntity save(@RequestBody @Valid BookingDTO bookingDTO, BindingResult result, UriComponentsBuilder builder) {
        return super.save(BookingConverter.toBooking(bookingDTO), result, builder);
    }

    /**
     * Update the Booking of a given id with the given status.
     *
     * @param id     The id
     * @param status The BookingStatus
     * @return The updated entity
     */
    @RequestMapping(method = PUT, value = "/{id}/status/{status}")
    public ResponseEntity updateStatus(@PathVariable Integer id, @PathVariable BookingStatus status) {
        this.bookingService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
