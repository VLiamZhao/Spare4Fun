package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.entity.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationDaoTest {
    @Autowired
    private LocationDao locationDao;

    private List<Location> locations;

    @BeforeEach
    public void setup() {
        locations = dummyLocations();

        locations.forEach( location -> {
                    locationDao.saveLocation(location);
                });
    }

    @AfterEach
    public void clean() {
        locations.forEach(location -> {
                    locationDao.deleteLocationById(location.getId());
                });
    }

    public List<Location> dummyLocations() {
        List<Location> res = new ArrayList<>();

        Location loc1 = Location
                .builder()
                .line1("1")
                .city("A")
                .state("WA")
                .build();
        res.add(loc1);

        Location loc2 = Location
                .builder()
                .line1("2")
                .city("B")
                .state("CA")
                .build();
        res.add(loc2);

        return res;
    }

    @Test
    public void testSaveLocation() {
        locations.forEach(location -> {
                    assertThat(locationDao.getLocationById(location.getId())).isNotNull();
                });
    }

    @Test
    public void testDeleteLocation() {
        Location loc = Location
                .builder()
                .line1("3")
                .city("C")
                .state("NY")
                .build();
        locationDao.saveLocation(loc);
        assertThat(locationDao.getLocationById(loc.getId())).isNotNull();
        locationDao.deleteLocationById(loc.getId());
        assertThat(locationDao.getLocationById(loc.getId())).isNull();
    }

    @Test
    public void testGetLocationById() {
        Location loc = locationDao.getLocationById(locations.get(0).getId());
        assertThat(loc.getId()).isEqualTo(locations.get(0).getId());
        assertThat(loc.getState()).isEqualTo(locations.get(0).getState());
    }

    @Test
    public void testGetAllLocations() {
        List<Location> locs = locationDao.getAllLocations();
        assertThat(locs.size()).isEqualTo(locations.size());
    }
}
