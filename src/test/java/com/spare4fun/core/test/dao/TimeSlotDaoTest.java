package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.*;
import com.spare4fun.core.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TimeSlotDaoTest {
    @Autowired
    private TimeSlotDao timeSlotDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    private Location location;
    private User buyer;
    private User seller;
    private Item item;
    private Offer offer;
    private Appointment appointment;
    private List<TimeSlot> timeSlots;

    @BeforeEach
    public void setup() {
        location = Location
                .builder()
                .line1("1")
                .city("A")
                .state("WA")
                .build();
        locationDao.saveLocation(location);

        seller = User
                .builder()
                .email("dummy1")
                .password("pass")
                .build();
        buyer = User
                .builder()
                .email("dummy2")
                .password("pass")
                .build();

        try {
            userDao.saveUser(seller);
            userDao.saveUser(buyer);
        } catch(Exception e) {
            e.printStackTrace();
        }

        item = Item
                .builder()
                .seller(seller)
                .location(location)
                .title("CLRS")
                .quantity(3)
                .build();
        itemDao.saveItem(item);

        offer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .price(10)
                .quantity(1)
                .message("123")
                .build();
        offerDao.saveOffer(offer);

        appointment = Appointment
                .builder()
                .item(item)
                .offer(offer)
                .buyer(buyer)
                .seller(seller)
                .location(location)
                .build();
        appointmentDao.saveAppointment(appointment);

        timeSlots = dummyTimeSlots();
        timeSlots.forEach(timeSlot -> {
            timeSlotDao.saveTimeSlot(timeSlot);
        });
    }

    @AfterEach
    public void clean() {
        timeSlots.forEach(timeSlot -> {
            timeSlotDao.deleteTimeSlotById(timeSlot.getId());
        });
        appointmentDao.deleteAppointmentById(appointment.getId());
        // offerDao.deleteOfferById(offer.getId());
        itemDao.deleteItemById(item.getId());
        userDao.deleteUserById(seller.getId());
        userDao.deleteUserById(buyer.getId());
        locationDao.deleteLocationById(location.getId());
    }

    private List<TimeSlot> dummyTimeSlots() {
        List<TimeSlot> res = new ArrayList<>();

        res.add(
                TimeSlot
                        .builder()
                        .appointment(appointment)
                        .startTime("2020-12-04T21:26:45.059Z") // js: new Date().toISOString()
                        .endTime("2020-12-04T21:42:43.509Z") // js: new Date(d1.getTime() + minutes * 60000)
                        .build()
        );

        res.add(
                TimeSlot
                        .builder()
                        .appointment(appointment)
                        .startTime("2020-12-04T21:36:45.059Z") // js: new Date().toISOString()
                        .endTime("2020-12-04T21:52:43.509Z") // js: new Date(d1.getTime() + minutes * 60000)
                        .build()
        );

        return res;
    }

    @Test
    public void testSaveTimeSlot() {
        timeSlots.forEach(timeSlot -> {
            assertThat(timeSlotDao.getTimeSlotById(timeSlot.getId())).isNotNull();
        });
    }

    @Test
    public void testDeleteTimeSlot() {
        TimeSlot timeSlot = TimeSlot
                .builder()
                .appointment(appointment)
                .startTime("2020-12-04T21:16:45.059Z") // js: new Date().toISOString()
                .endTime("2020-12-04T21:32:43.509Z") // js: new Date(d1.getTime() + minutes * 60000)
                .build();
        timeSlotDao.saveTimeSlot(timeSlot);
        assertThat(timeSlotDao.getTimeSlotById(timeSlot.getId())).isNotNull();
        timeSlotDao.deleteTimeSlotById(timeSlot.getId());
        assertThat(timeSlotDao.getTimeSlotById(timeSlot.getId())).isNull();
    }

    @Test
    public void testGetTimeSlotById() {
        TimeSlot timeSlot = timeSlotDao.getTimeSlotById(timeSlots.get(0).getId());
        assertThat(timeSlot.getAppointment().getId()).isEqualTo(appointment.getId());
        assertThat(timeSlot.getStartTime()).isEqualTo(timeSlots.get(0).getStartTime());
        assertThat(timeSlot.getEndTime()).isEqualTo(timeSlots.get(0).getEndTime());
    }

    @Test
    public void testGetTimeSlotByAppointment() {
        List<TimeSlot> timeSlotsFromDB = timeSlotDao.getAllTimeSlot(appointment.getId());
        assertThat(timeSlotsFromDB.size()).isEqualTo(timeSlots.size());
        int appointmentNotExist = appointment.getId() + 1;
        assertTrue(timeSlotDao.getAllTimeSlot(appointmentNotExist).isEmpty());
    }
}
