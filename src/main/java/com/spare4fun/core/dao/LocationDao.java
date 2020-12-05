package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Location;

public interface LocationDao {
    //Please ignore this method. This is only for temporary test
    Location saveLocation(Location location);

    Location deleteLocationById(int locationId);
}
