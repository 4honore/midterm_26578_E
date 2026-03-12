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
 * EXPLANATION OF SAVING LOCATION (Self-Referencing Hierarchy):
 * - To fulfill the requirement of storing the full path down to village in one table,
 *   we check if each level (Province -> District -> Sector -> Cell -> Village) exists.
 * - If not, we create it and set its `parent` to the level above it.
 * - This builds the hierarchy perfectly normalized and allows traversing up/down.
 */
@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
@Transactional
    public Location saveLocation(LocationDTO dto) {
        // 1. Province (Root)
        Location province = locationRepository.findByNameAndLocationTypeAndParentIsNull(dto.getProvinceName(), "PROVINCE")
            .orElseGet(() -> {
                Location p = new Location();
                p.setCode(dto.getProvinceCode());
                p.setName(dto.getProvinceName());
                p.setLocationType("PROVINCE");
                return locationRepository.save(p);
            });

        // 2. District
        Location district = locationRepository.findByNameAndLocationTypeAndParent(dto.getDistrict(), "DISTRICT", province)
            .orElseGet(() -> {
                Location d = new Location();
                d.setName(dto.getDistrict());
                d.setLocationType("DISTRICT");
                d.setParent(province);
                return locationRepository.save(d);
            });

        // 3. Sector
        Location sector = locationRepository.findByNameAndLocationTypeAndParent(dto.getSector(), "SECTOR", district)
            .orElseGet(() -> {
                Location s = new Location();
                s.setName(dto.getSector());
                s.setLocationType("SECTOR");
                s.setParent(district);
                return locationRepository.save(s);
            });

        // 4. Cell
        Location cell = locationRepository.findByNameAndLocationTypeAndParent(dto.getCell(), "CELL", sector)
            .orElseGet(() -> {
                Location c = new Location();
                c.setName(dto.getCell());
                c.setLocationType("CELL");
                c.setParent(sector);
                return locationRepository.save(c);
            });

        // 5. Village
        Location village = locationRepository.findByNameAndLocationTypeAndParent(dto.getVillage(), "VILLAGE", cell)
            .orElseGet(() -> {
                Location v = new Location();
                v.setName(dto.getVillage());
                v.setLocationType("VILLAGE");
                v.setHospitalName(dto.getHospitalName());
                v.setParent(cell);
                return locationRepository.save(v);
            });

        return village; // Returns the lowest level (Village)
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


