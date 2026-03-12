package com.medilink.medilink.HMS.controller;

import com.medilink.medilink.HMS.entity.User;
import com.medilink.medilink.HMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController - REST API endpoints for User management
 * 
 * EXPLANATION OF PROVINCE QUERY ENDPOINTS:
 * 
 * 1. GET /api/users/search?provinceName=Kigali City
 *    - Retrieves all users working in the specified province by name
 *    - Uses nested property query: location.provinceName
 *    - Returns list of users
 * 
 * 2. GET /api/users/search?provinceCode=KC
 *    - Retrieves all users working in the specified province by code
 *    - Uses nested property query: location.provinceCode
 *    - Returns list of users
 * 
 * IMPORTANT: Location Data Structure
 * - Users are linked to Village-level locations only
 * - Due to self-referencing Location relationships, Villages are automatically
 *   linked to Cell -> Sector -> District -> Province
 * - This allows users to be retrieved by ANY administrative level
 * 
 * These endpoints demonstrate the exam requirement:
 * "Retrieve all users from a given province using province code OR province name"
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsersPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByProvince(
            @RequestParam(required = false) String provinceName,
            @RequestParam(required = false) String provinceCode) {

        if (provinceName != null)
            return ResponseEntity.ok(userService.getUsersByProvinceName(provinceName));

        if (provinceCode != null)
            return ResponseEntity.ok(userService.getUsersByProvinceCode(provinceCode));

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
