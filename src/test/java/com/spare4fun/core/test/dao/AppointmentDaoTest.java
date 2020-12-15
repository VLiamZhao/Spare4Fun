package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.*;
import com.spare4fun.core.entity.*;
import com.spare4fun.core.exception.InvalidActionException;
import com.spare4fun.core.exception.InvalidAppointmentException;
import com.spare4fun.core.exception.InvalidTimeSlotException;
import com.spare4fun.core.exception.InvalidUserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AppointmentDaoTest {
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

    // test with following 2 dummy appointments
    private Location location;
    private User buyer1;
    private User buyer2;
    private User seller;
    private Item item;
    private Offer offer1;
    private Offer offer2;
    private Appointment appointment1;
    private Appointment appointment2;
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
        buyer1 = User
                .builder()
                .email("dummy2")
                .password("pass")
                .build();
        buyer2 = User
                .builder()
                .email("dummy3")
                .password("pass")
                .build();
        try {
            userDao.saveUser(seller);
            userDao.saveUser(buyer1);
            userDao.saveUser(buyer2);
        } catch(Exception e) {
            e.printStackTrace();
        }

        item = Item
                .builder()
                .seller(seller)
                .location(location)
                .title("CLRS")
                .quantity(4)
                .build();
        itemDao.saveItem(item);

        offer1 = Offer
                .builder()
                .item(item)
                .buyer(buyer1)
                .seller(seller)
                .price(10)
                .quantity(1)
                .message("123")
                .build();
        offerDao.saveOffer(offer1);

        offer2 = Offer
                .builder()
                .item(item)
                .buyer(buyer2)
                .seller(seller)
                .price(12)
                .quantity(2)
                .message("123")
                .build();
        offerDao.saveOffer(offer2);

        appointment1 = Appointment
                .builder()
                .item(item)
                .offer(offer1)
                .buyer(buyer1)
                .seller(seller)
                .location(location)
                .build();
        appointmentDao.saveAppointment(appointment1);

        appointment2 = Appointment
                .builder()
                .item(item)
                .offer(offer2)
                .buyer(buyer2)
                .seller(seller)
                .location(location)
                .build();
        appointmentDao.saveAppointment(appointment2);

        timeSlots = dummyTimeSlots();
        timeSlots.forEach(timeSlot -> {
            timeSlotDao.saveTimeSlot(timeSlot);
        });
    }

    @AfterEach
    public void clean() {
        if (appointmentDao.getAppointmentById(appointment1.getId()) != null) {
            appointmentDao.deleteAppointmentById(appointment1.getId());
        }
        appointmentDao.deleteAppointmentById(appointment2.getId());
        offerDao.deleteOfferById(offer1.getId());
        offerDao.deleteOfferById(offer2.getId());
        itemDao.deleteItemById(item.getId());
        userDao.deleteUserById(seller.getId());
        userDao.deleteUserById(buyer1.getId());
        userDao.deleteUserById(buyer2.getId());
        locationDao.deleteLocationById(location.getId());
    }

    private List<TimeSlot> dummyTimeSlots() {
        List<TimeSlot> res = new ArrayList<>();

        Instant startTime = Instant.now();
        String start = startTime.plus(1, DAYS).toString();
        String end = startTime.plus(2, DAYS).toString();

        res.add(
                TimeSlot
                        .builder()
                        .appointment(appointment1)
                        // js: new Date().toISOString()
                        .startTime(start)
                        // js: new Date(d1.getTime() + minutes * 60000)
                        .endTime(end)
                        .build()
        );

        res.add(
                TimeSlot
                        .builder()
                        .appointment(appointment2)
                        // js: new Date().toISOString()
                        .startTime(start)
                        // js: new Date(d1.getTime() + minutes * 60000)
                        .endTime(end)
                        .build()
        );

        return res;
    }

    @Test
    public void testSaveAppointment() {
        Appointment app1 = appointmentDao.getAppointmentById(appointment1.getId());
        assertTrue(app1 != null);
    }

    @Test
    public void testDeleteAppointment() {
        appointmentDao.deleteAppointmentById(appointment1.getId());
        assertTrue(appointmentDao.getAppointmentById(appointment1.getId()) == null);
    }

    @Test
    public void testGetAppointmentById() {
        Appointment app1 = appointmentDao.getAppointmentById(appointment1.getId());
        assertTrue(app1 != null);
        assertTrue(app1.getId() == appointment1.getId());
        assertTrue(app1.getBuyer().getId() == appointment1.getBuyer().getId());
        assertTrue(app1.getSeller().getId() == appointment1.getSeller().getId());
        assertTrue(app1.getOffer().getId() == offer1.getId());
        assertTrue(app1.getItem().getId() == item.getId());
        // assertTrue(app1.getAvailTime().size() == 1);
        assertTrue(timeSlotDao.getAllTimeSlot(app1.getId()).size() == 1);
    }

    @Test
    public void testGetAppointmentBuyer() {
        List<Appointment> list1 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), true, true);
        assertThat(list1.size()).isEqualTo(1);

        List<Appointment> list2 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), false, true);
        list2.forEach(appointment -> {
            System.out.println(appointment.getId());
        });
        assertThat(list2.size()).isEqualTo(1);

        List<Appointment> list3 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), true, false);
        assertThat(list3.size()).isEqualTo(0);

        List<Appointment> list4 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), false, false);
        assertThat(list4.size()).isEqualTo(0);

        List<Appointment> list5 = appointmentDao.getAllAppointmentsBuyer(
                seller.getId(), false, false);
        assertThat(list5.size()).isEqualTo(0);
    }

    @Test
    public void testGetAppointmentSeller() {
        List<Appointment> list1 = appointmentDao.getAllAppointmentsSeller(
                seller.getId(), item.getId(), true, true);
        assertThat(list1.size()).isEqualTo(2);

        List<Appointment> list2 = appointmentDao.getAllAppointmentsSeller(
                seller.getId(), item.getId(), false, true);
        assertThat(list2.size()).isEqualTo(2);

        List<Appointment> list3 = appointmentDao.getAllAppointmentsSeller(
                seller.getId(), item.getId(), true, false);
        assertThat(list3.size()).isEqualTo(0);

        List<Appointment> list4 = appointmentDao.getAllAppointmentsSeller(
                seller.getId(), item.getId(), false, false);
        assertThat(list4.size()).isEqualTo(0);

        List<Appointment> list5 = appointmentDao.getAllAppointmentsSeller(
                buyer1.getId(), item.getId(),false, false);
        assertThat(list5.size()).isEqualTo(0);
    }

    @Test
    public void testConfirmAppointment() {
        Instant current = Instant.now();

        Appointment app1 = appointmentDao.confirmAppointmentWithTimeSlot(
                appointment1.getId(), timeSlots.get(0).getId(), buyer1.getId(), current.toString());

        assertTrue(app1.isConfirmed());

        List<Appointment> list1 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), true, false);
        assertThat(list1.size()).isEqualTo(1);

        List<Appointment> list2 = appointmentDao.getAllAppointmentsBuyer(
                buyer1.getId(), false, true);
        assertThat(list2.size()).isEqualTo(0);

        assertThrows(InvalidAppointmentException.class, () -> {
            appointmentDao.confirmAppointmentWithTimeSlot(
                    appointment1.getId(), timeSlots.get(0).getId(), buyer1.getId(), current.toString());
        });

        assertThrows(InvalidUserException.class, () -> {
            appointmentDao.confirmAppointmentWithTimeSlot(
                    appointment2.getId(), timeSlots.get(0).getId(), buyer1.getId(), current.toString());
        });

        assertThrows(InvalidTimeSlotException.class, () -> {
            appointmentDao.confirmAppointmentWithTimeSlot(
                    appointment2.getId(), timeSlots.get(0).getId(), buyer2.getId(), current.toString());
        });

        assertThrows(InvalidActionException.class, () -> {
            appointmentDao.confirmAppointmentWithTimeSlot(
                    appointment2.getId(), timeSlots.get(1).getId(), buyer2.getId(), current.plus(2, DAYS).toString());
        });
    }
}
