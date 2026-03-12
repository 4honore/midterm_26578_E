# Medilink HMS API - Postman Testing Guide

## ⚠️ CRITICAL REQUIREMENTS - READ FIRST

Based on the project specifications, ensure:

1. **Single Location Table** - Use ONE `locations` table with **self-referencing relationships** (parent_location_id)
   - Do NOT create separate tables for Province, District, Sector, Cell, Village
   
2. **Location Hierarchy** - Implement Rwanda's administrative structure:
   ```
   Province → District → Sector → Cell → Village
   ```

3. **User-Location Link** - Users MUST be linked to **Village-level locations ONLY**
   - When creating a User, reference the Village ID
   - This automatically connects the user to all parent levels through relationships

4. **Province Query Requirement** - Implement endpoints to retrieve users by:
   - Province Name: `GET /api/users/search?provinceName=Kigali City`
   - Province Code: `GET /api/users/search?provinceCode=KC`
   - This demonstrates your self-referencing relationship is working correctly

5. **Data Creation Workflow** - Use the LocationDTO to create the FULL hierarchy in one call
   - POST to `/api/locations` with flat JSON containing all admin levels
   - The system creates Province → District → Sector → Cell → Village in one transaction
   - Returns the Village entity ID for use in other entities

---


1. Import `Medilink_HMS_API.postman_collection.json` into Postman
2. Ensure the application is running on `http://localhost:8080`
3. Test each endpoint with the provided sample requests

---

## API Endpoints Overview

### Base URL
```
http://localhost:8080
```

---

## 1. PATIENTS API
Base Path: `/api/patients`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/patients` | Create a new patient |
| GET | `/api/patients` | Get all patients (paginated) |
| GET | `/api/patients/{id}` | Get patient by ID |
| PUT | `/api/patients/{id}` | Update patient |
| DELETE | `/api/patients/{id}` | Delete patient |

### Create Patient - Sample Request
```json
{
  "nationalId": "1234567890123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+250722123456",
  "dateOfBirth": "1990-05-15"
}
```

### Get All Patients - Query Parameters
```
/api/patients?page=0&size=10
```

---

## 2. DOCTORS API
Base Path: `/api/doctors`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/doctors` | Create a new doctor |
| GET | `/api/doctors` | Get all doctors (paginated) |
| GET | `/api/doctors/{id}` | Get doctor by ID |
| PUT | `/api/doctors/{id}` | Update doctor |
| DELETE | `/api/doctors/{id}` | Delete doctor |

### Create Doctor - Sample Request
```json
{
  "name": "Dr. Jane Smith",
  "specialization": "Cardiology",
  "phone": "+250722456789",
  "location": {
    "id": 1
  }
}
```

---

## 3. LOCATIONS API
Base Path: `/api/locations`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/locations` | Create location hierarchy (Rwanda Administrative Structure) |
| GET | `/api/locations` | Get all locations (paginated) |
| GET | `/api/locations/{id}` | Get location by ID |
| PUT | `/api/locations/{id}` | Update location |
| DELETE | `/api/locations/{id}` | Delete location |

### Create Location (Rwanda Hierarchy) - Sample Request
```json
{
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Gasabo",
  "sector": "Ndera",
  "cell": "Nyarugenge",
  "village": "Kigali Central",
  "hospitalName": "Central Hospital Kigali"
}
```

---

## 4. APPOINTMENTS API
Base Path: `/api/appointments`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/appointments` | Create a new appointment |
| GET | `/api/appointments` | Get all appointments (paginated) |
| GET | `/api/appointments/{id}` | Get appointment by ID |
| PUT | `/api/appointments/{id}` | Update appointment |
| DELETE | `/api/appointments/{id}` | Delete appointment |

### Create Appointment - Sample Request
```json
{
  "appointmentDate": "2026-03-20",
  "reason": "General Checkup",
  "status": "SCHEDULED",
  "patient": {
    "id": 1
  },
  "doctor": {
    "id": 1
  },
  "location": {
    "id": 1
  }
}
```

---

## 5. MEDICAL RECORDS API
Base Path: `/api/medical-records`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/medical-records` | Create a new medical record |
| GET | `/api/medical-records` | Get all medical records (paginated) |
| GET | `/api/medical-records/{id}` | Get medical record by ID |
| PUT | `/api/medical-records/{id}` | Update medical record |
| DELETE | `/api/medical-records/{id}` | Delete medical record |

### Create Medical Record - Sample Request
```json
{
  "recordDate": "2026-03-11",
  "diagnosis": "Hypertension",
  "treatment": "Prescribed Lisinopril 10mg",
  "notes": "Patient advised to reduce sodium intake and exercise regularly",
  "patient": {
    "id": 1
  }
}
```

---

## 6. USERS API
Base Path: `/api/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create a new user |
| GET | `/api/users` | Get all users (paginated) |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/search` | Search users by province name or code |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Create User - Sample Request
```json
{
  "username": "admin_user",
  "password": "SecurePassword123",
  "role": "ADMIN",
  "location": {
    "id": 1
  }
}
```

### Search Users by Province Name
```
GET /api/users/search?provinceName=Kigali City
```

### Search Users by Province Code
```
GET /api/users/search?provinceCode=KC
```

---

## Testing Workflow - Rwanda Administrative Hierarchy

### **CRITICAL: Location Hierarchy Structure**
The system uses a **self-referencing Location table** with this Rwanda administrative hierarchy:
```
Province → District → Sector → Cell → Village
```

**Key Points:**
- Each level is stored in the SAME `locations` table with self-referencing relationships
- A **parent_location_id** field links each level to the level above it
- Users are **ONLY linked to Village-level locations**
- Due to self-referencing relationships, querying a Village automatically includes Cell → Sector → District → Province

### **Required Testing Sequence:**

#### **Step 1: Create FULL Location Hierarchy** ⭐ DO THIS FIRST
Create the entire Rwanda administrative structure in ONE POST request using LocationDTO:

```
POST /api/locations
```

**Sample Request (Province → District → Sector → Cell → Village):**
```json
{
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Gasabo",
  "sector": "Ndera",
  "cell": "Nyarugenge",
  "village": "Kigali Central",
  "hospitalName": "Central Hospital Kigali"
}
```

**What this does:**
- Creates Province "Kigali City" (if doesn't exist)
- Creates District "Gasabo" linked to Province
- Creates Sector "Ndera" linked to District
- Creates Cell "Nyarugenge" linked to Sector
- Creates Village "Kigali Central" linked to Cell
- Returns the **Village ID** (this is what you'll use for Users, Doctors, etc.)

**Result:** You get back a Location entity with the Village details. **Save this ID for later use!**

---

#### **Step 2: Create Patients & Doctors**
Now create patients and doctors (these don't require location references initially):

```
POST /api/patients
POST /api/doctors
```

**Doctor Creation (requires location):**
```json
{
  "name": "Dr. Jane Smith",
  "specialization": "Cardiology",
  "phone": "+250722456789",
  "location": {
    "id": 5
  }
}
```
Use the **Village ID** from Step 1 as the location.id

---

#### **Step 3: Create Users** 
Create system users linked to the Village location:

```
POST /api/users
```

```json
{
  "username": "admin_user",
  "password": "SecurePassword123",
  "role": "ADMIN",
  "location": {
    "id": 5
  }
}
```
Use the **Village ID** from Step 1 as the location.id

---

#### **Step 4: Test Province Queries** ⭐ VERIFY RELATIONSHIPS
This proves the self-referencing hierarchy is working:

```
GET /api/users/search?provinceName=Kigali City
```

**Expected Result:** Returns all users in Kigali City province
- Due to relationships, if user is linked to Village "Kigali Central"
- That Village is linked to Cell "Nyarugenge"
- Which is linked to Sector "Ndera"
- Which is linked to District "Gasabo"
- Which is linked to Province "Kigali City"
- So the query finds the user!

OR use province code:
```
GET /api/users/search?provinceCode=KC
```

---

#### **Step 5: Create Appointments**
Link patients with doctors at the village location:

```
POST /api/appointments
```

```json
{
  "appointmentDate": "2026-03-20",
  "reason": "General Checkup",
  "status": "SCHEDULED",
  "patient": {
    "id": 1
  },
  "doctor": {
    "id": 1
  },
  "location": {
    "id": 5
  }
}
```

---

#### **Step 6: Create Medical Records**
Add medical history for patients:

```
POST /api/medical-records
``````

---

## Common Issues & Solutions

### Issue: 400 Bad Request
- Ensure all required fields are provided in the request body
- Check date formats (use ISO format: YYYY-MM-DD)
- Verify that referenced IDs exist (location_id, patient_id, doctor_id)

### Issue: 404 Not Found
- The requested resource doesn't exist
- Verify the ID in the URL path is correct

### Issue: 409 Conflict
- For Patient: National ID must be unique
- For User: Username must be unique

---

## Pagination Details

All GET requests support pagination:
```
?page=0&size=10
```
- `page`: 0-based page number (default: 0)
- `size`: Number of records per page (default: 10)

---

## Response Status Codes

| Code | Meaning |
|------|---------|
| 201 | Created (POST successful) |
| 200 | OK (GET, PUT successful) |
| 204 | No Content (DELETE successful) |
| 400 | Bad Request |
| 404 | Not Found |
| 409 | Conflict (Duplicate data) |
| 500 | Internal Server Error |

---

## Import into Postman

1. Open Postman
2. Click **Import** button
3. Select **File** tab
4. Choose `Medilink_HMS_API.postman_collection.json`
5. Click **Import**
6. The collection will be added to your workspace with all endpoints pre-configured
