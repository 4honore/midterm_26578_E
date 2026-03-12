# 🎯 FINAL SUBMISSION CHECKLIST - Medilink HMS

## ⏰ DEADLINE: Final Week - NO EXTENSIONS
All work must be pushed to repository before deadline.

---

## ✅ PROJECT REQUIREMENTS CHECKLIST

### 1. **Location Table (Self-Referencing Hierarchy)**
- [ ] Single `locations` table created
- [ ] Self-referencing relationship with `parent_location_id`
- [ ] Supports Rwanda administrative hierarchy: **Province → District → Sector → Cell → Village**
- [ ] LocationDTO accepts flat JSON and creates full hierarchy in one call
- [ ] Correctly saves Province, District, Sector, Cell, Village levels

### 2. **User Management with Province Queries**
- [ ] Users are linked to **Village-level locations ONLY**
- [ ] Endpoint: `GET /api/users/search?provinceName=Kigali City` ✅ Works
- [ ] Endpoint: `GET /api/users/search?provinceCode=KC` ✅ Works
- [ ] User queries return correct results based on location hierarchy
- [ ] Self-referencing relationships properly traverse: Village → Cell → Sector → District → Province

### 3. **All CRUD Endpoints Implemented**

#### Patients (`/api/patients`)
- [ ] `POST /api/patients` - Create patient
- [ ] `GET /api/patients` - Get all (paginated)
- [ ] `GET /api/patients/{id}` - Get by ID
- [ ] `PUT /api/patients/{id}` - Update
- [ ] `DELETE /api/patients/{id}` - Delete

#### Doctors (`/api/doctors`)
- [ ] `POST /api/doctors` - Create doctor (with location)
- [ ] `GET /api/doctors` - Get all (paginated)
- [ ] `GET /api/doctors/{id}` - Get by ID
- [ ] `PUT /api/doctors/{id}` - Update
- [ ] `DELETE /api/doctors/{id}` - Delete

#### Locations (`/api/locations`)
- [ ] `POST /api/locations` - Create location hierarchy
- [ ] `GET /api/locations` - Get all (paginated)
- [ ] `GET /api/locations/{id}` - Get by ID
- [ ] `PUT /api/locations/{id}` - Update
- [ ] `DELETE /api/locations/{id}` - Delete

#### Appointments (`/api/appointments`)
- [ ] `POST /api/appointments` - Create appointment
- [ ] `GET /api/appointments` - Get all (paginated)
- [ ] `GET /api/appointments/{id}` - Get by ID
- [ ] `PUT /api/appointments/{id}` - Update
- [ ] `DELETE /api/appointments/{id}` - Delete

#### Medical Records (`/api/medical-records`)
- [ ] `POST /api/medical-records` - Create record
- [ ] `GET /api/medical-records` - Get all (paginated)
- [ ] `GET /api/medical-records/{id}` - Get by ID
- [ ] `PUT /api/medical-records/{id}` - Update
- [ ] `DELETE /api/medical-records/{id}` - Delete

#### Users (`/api/users`)
- [ ] `POST /api/users` - Create user
- [ ] `GET /api/users` - Get all (paginated)
- [ ] `GET /api/users/{id}` - Get by ID
- [ ] `GET /api/users/search` - Search by province (name or code) ✅
- [ ] `PUT /api/users/{id}` - Update
- [ ] `DELETE /api/users/{id}` - Delete

### 4. **Relationships Implemented**
- [ ] Patient → Appointments (1:M)
- [ ] Patient → Medical Records (1:M)
- [ ] Patient ↔ Doctor (M:M)
- [ ] Doctor → Appointments (1:M)
- [ ] Doctor → Location (M:1)
- [ ] User → Location (M:1)
- [ ] User → UserProfile (1:1)
- [ ] Location → Location (Self-referencing, M:1 for hierarchy)

### 5. **Data Validation**
- [ ] Patient National ID is unique
- [ ] User Username is unique
- [ ] Proper HTTP status codes (201 Created, 200 OK, 204 No Content, 400 Bad Request, 404 Not Found, 409 Conflict)
- [ ] Pagination works for all GET endpoints
- [ ] Pagination defaults: page=0, size=10

### 6. **Repository Setup**
- [ ] Repository name format: `midterm_studentID_group`
- [ ] All code is committed and pushed
- [ ] No uncommitted changes before deadline

### 7. **Testing (Postman Collection Provided)**
- [ ] Import `Medilink_HMS_API.postman_collection.json`
- [ ] Test all endpoints with sample data
- [ ] Verify province search queries work correctly
- [ ] Verify Location hierarchy creation
- [ ] Verify User-Location relationships

---

## 📋 TESTING WORKFLOW

### Order to Test (Do NOT skip steps):

1. **Create Location Hierarchy First**
   ```
   POST /api/locations
   Request body must include: provinceCode, provinceName, district, sector, cell, village, hospitalName
   ```
   **Save the returned Location ID (Village)**

2. **Create Patients**
   ```
   POST /api/patients
   Include: nationalId (unique), firstName, lastName, phone, dateOfBirth
   ```

3. **Create Doctors**
   ```
   POST /api/doctors
   Include: name, specialization, phone, location (use Village ID from Step 1)
   ```

4. **Create Users**
   ```
   POST /api/users
   Include: username (unique), password, role, location (use Village ID from Step 1)
   ```

5. **Test Province Queries**
   ```
   GET /api/users/search?provinceName=Kigali City
   GET /api/users/search?provinceCode=KC
   ```
   **Should return the users you created** ✅

6. **Create Appointments**
   ```
   POST /api/appointments
   Link patient, doctor, location (use Village ID)
   ```

7. **Create Medical Records**
   ```
   POST /api/medical-records
   Link to patient
   ```

---

## 🚀 BEFORE FINAL PUSH

- [ ] Run `./mvnw.cmd clean install` - builds successfully
- [ ] All tests pass (if any test files exist)
- [ ] No compiler errors or warnings
- [ ] Application starts without errors: `./mvnw.cmd spring-boot:run`
- [ ] Test all endpoints with Postman collection
- [ ] Verify databases created with correct structure
- [ ] All relationships work (try fetching related entities)
- [ ] Province queries return correct users
- [ ] Changes committed: `git add .` → `git commit -m "Final project submission"`
- [ ] Changes pushed: `git push origin main` (or your branch)

---

## ⚠️ COMMON MISTAKES TO AVOID

❌ **Don't:** Create separate tables for Province, District, Sector, Cell, Village
✅ **Do:** Use ONE `locations` table with self-referencing relationships

❌ **Don't:** Link Users to Province/District/Sector/Cell level locations
✅ **Do:** Only link Users to Village-level locations

❌ **Don't:** Forget to test province search queries
✅ **Do:** Verify `GET /api/users/search?provinceName=...` works

❌ **Don't:** Push uncommitted code
✅ **Do:** Commit frequently and push before deadline

❌ **Don't:** Miss the deadline for extensions
✅ **Do:** Submit early to avoid last-minute issues

---

## 📞 NEED HELP?

If you have questions:
- Review the instructor responses in the group chat
- Check the Rwanda Location Structure reference (https://kindly-mouth-eff.notion.site/Rwanda-Location-Structure-31e08f5a673880f394e8ceb65d4be927)
- Test endpoints using the Postman collection
- Contact instructor/TAs for clarification

**Good luck! You got this! 🎉**
