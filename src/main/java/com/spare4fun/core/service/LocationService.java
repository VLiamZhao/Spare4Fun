package com.spare4fun.core.service;

import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    @Autowired
    private LocationDao locationDao;

    public Location saveLocation(Location location) {
        return locationDao.saveLocation(location);
    }

    public void deleteLocationById(int locationId) {
        locationDao.deleteLocationById(locationId);
    }
}
