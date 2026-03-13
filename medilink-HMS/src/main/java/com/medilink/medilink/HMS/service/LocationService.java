package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.Location;
import com.medilink.medilink.HMS.entity.LocationDTO;
import com.medilink.medilink.HMS.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────

/**
 * LocationService Interface - Defines business logic operations for Location
 */


// ─── Implementation ──────────────────────────────────────────────────────────

/**
 * LocationServiceImpl - Implementation of LocationService
 *
 * EXPLANATION OF SAVING LOCATION (Hierarchical - Adjacency List Model):
 * - Builds the hierarchy by creating each level and linking to its parent
 * - Province (root) → District → Sector → Cell → Village
 * - Each location stores reference to its parent
 * - This creates a tree structure that can be navigated up or down
 */
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location saveLocation(LocationDTO dto) {
        // 1. Province (Root - no parent)
        Location province = locationRepository.findByNameAndLocationTypeAndParentIsNull(
                dto.getProvinceName(), Location.LocationType.PROVINCE)
            .orElseGet(() -> {
                Location p = new Location();
                p.setCode(dto.getProvinceCode());
                p.setName(dto.getProvinceName());
                p.setLocationType(Location.LocationType.PROVINCE);
                return locationRepository.save(p);
            });

        // 2. District (parent = Province)
        Location district = locationRepository.findByNameAndLocationTypeAndParent(
                dto.getDistrict(), Location.LocationType.DISTRICT, province)
            .orElseGet(() -> {
                Location d = new Location();
                d.setName(dto.getDistrict());
                d.setLocationType(Location.LocationType.DISTRICT);
                d.setParent(province);
                return locationRepository.save(d);
            });

        // 3. Sector (parent = District)
        Location sector = locationRepository.findByNameAndLocationTypeAndParent(
                dto.getSector(), Location.LocationType.SECTOR, district)
            .orElseGet(() -> {
                Location s = new Location();
                s.setName(dto.getSector());
                s.setLocationType(Location.LocationType.SECTOR);
                s.setParent(district);
                return locationRepository.save(s);
            });

        // 4. Cell (parent = Sector)
        Location cell = locationRepository.findByNameAndLocationTypeAndParent(
                dto.getCell(), Location.LocationType.CELL, sector)
            .orElseGet(() -> {
                Location c = new Location();
                c.setName(dto.getCell());
                c.setLocationType(Location.LocationType.CELL);
                c.setParent(sector);
                return locationRepository.save(c);
            });

        // 5. Village (parent = Cell, lowest level)
        Location village = locationRepository.findByNameAndLocationTypeAndParent(
                dto.getVillage(), Location.LocationType.VILLAGE, cell)
            .orElseGet(() -> {
                Location v = new Location();
                v.setName(dto.getVillage());
                v.setLocationType(Location.LocationType.VILLAGE);
                v.setHospitalName(dto.getHospitalName());
                v.setParent(cell);
                return locationRepository.save(v);
            });

        return village; // Returns the Village (users are linked to this)
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Page<Location> getAllLocationsPaginated(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public Location updateLocation(Long id, LocationDTO dto) {
        Location existingLocation = getLocationById(id);
        existingLocation.setName(dto.getVillage());
        existingLocation.setHospitalName(dto.getHospitalName());
        return locationRepository.save(existingLocation);
    }

    public void deleteLocation(Long id) {
        Location location = getLocationById(id);
        locationRepository.delete(location);
    }
}


