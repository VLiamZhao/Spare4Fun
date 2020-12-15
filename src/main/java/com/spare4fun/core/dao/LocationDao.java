package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Location;

import java.util.List;

public interface LocationDao {
    /**
     * save a new Location to location table
     * @param location
     * @return location that is saved to table
     */
    Location saveLocation(Location location);

    /**
     * delete the location with locationId from location table
     * @author Yuhe Gu
     * @param locationId
     */
    void deleteLocationById(int locationId);

    /**
     * get a location by locationId
     * @param locationId
     * @return null iff the location doesn't exist
     */
    Location getLocationById(int locationId);

    /**
     * @return list of all locations in table
     */
    List<Location> getAllLocations();
}
