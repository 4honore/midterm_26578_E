package com.medilink.medilink.HMS.controller;

import com.medilink.medilink.HMS.entity.Location;
import com.medilink.medilink.HMS.entity.LocationDTO;
import com.medilink.medilink.HMS.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * LocationController - REST API endpoints for Location management
 * 
 * EXPLANATION OF ENDPOINTS:
 * 
 * 1. POST /api/locations - Create Location Hierarchy
 *    - Creates a self-referencing hierarchy (Province -> District -> Sector -> Cell -> Village)
 *    - Uses a flat JSON `LocationDTO` and translates it into hierarchical inserts
 *    - Returns 201 CREATED status with the Village-level Location entity
 *    - Request Body Example (Rwanda Administrative Hierarchy):
 *    {
 *      "provinceCode": "KC",
 *      "provinceName": "Kigali City",
 *      "district": "Gasabo",
 *      "sector": "Ndera",
 *      "cell": "Nyarugenge",
 *      "village": "Kigali Central",
 *      "hospitalName": "Central Hospital Kigali"
 *    }
 */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationDTO locationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationService.saveLocation(locationDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Location>> getAll(Pageable pageable) {
        return ResponseEntity.ok(locationService.getAllLocationsPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable Long id,
                                           @RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
