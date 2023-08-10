package com.vtm.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleHierarchy {
    private Region region;
    private Map<Fleet, List<Group>> fleetGroupsMap;

    public VehicleHierarchy(Region region) {
        this.region = region;
        this.fleetGroupsMap = new HashMap<>();
    }

    public void addFleet(Fleet fleet, List<Group> groups) {
        fleetGroupsMap.put(fleet, groups);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Map<Fleet, List<Group>> getFleetGroupsMap() {
        return fleetGroupsMap;
    }

    public void setFleetGroupsMap(Map<Fleet, List<Group>> fleetGroupsMap) {
        this.fleetGroupsMap = fleetGroupsMap;
    }
}
