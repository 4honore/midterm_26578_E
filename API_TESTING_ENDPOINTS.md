# Medilink HMS - Complete API Testing Guide

## Base URL
```
http://localhost:8080
```

---

## 1. LOCATIONS API (`/api/locations`)

### Create Location Hierarchy (Province → District → Sector → Cell → Village)
```bash
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "KC",
    "provinceName": "Kigali City",
    "district": "Gasabo",
    "sector": "Ndera",
    "cell": "Nyarugenge",
    "village": "Kigali Central",
    "hospitalName": "Central Hospital Kigali"
  }'
```

### Get All Locations (Paginated)
```bash
curl -X GET "http://localhost:8080/api/locations?page=0&size=10"
```

### Get Location by ID
```bash
curl -X GET http://localhost:8080/api/locations/1
```

### Update Location
```bash
curl -X PUT http://localhost:8080/api/locations/1 \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "KC",
    "provinceName": "Kigali City",
    "district": "Gasabo",
    "sector": "Ndera",
    "cell": "Nyarugenge",
    "village": "Kigali Central Updated",
    "hospitalName": "Central Hospital Kigali - New Building"
  }'
```

### Delete Location
```bash
curl -X DELETE http://localhost:8080/api/locations/1
```

---

## 2. PATIENTS API (`/api/patients`)

### Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "nationalId": "1234567890123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+250722123456",
    "dateOfBirth": "1990-05-15"
  }'
```

### Get All Patients (Paginated)
```bash
curl -X GET "http://localhost:8080/api/patients?page=0&size=10"
```

### Get Patient by ID
```bash
curl -X GET http://localhost:8080/api/patients/1
```

### Update Patient
```bash
curl -X PUT http://localhost:8080/api/patients/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nationalId": "1234567890123",
    "firstName": "John",
    "lastName": "Smith",
    "phone": "+250722999888",
    "dateOfBirth": "1990-05-15"
  }'
```

### Delete Patient
```bash
curl -X DELETE http://localhost:8080/api/patients/1
```

---

## 3. DOCTORS API (`/api/doctors`)

### Create Doctor (requires location ID from Step 1)
```bash
curl -X POST http://localhost:8080/api/doctors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. Jane Smith",
    "specialization": "Cardiology",
    "phone": "+250722456789",
    "location": {
      "id": 5
    }
  }'
```

### Get All Doctors (Paginated)
```bash
curl -X GET "http://localhost:8080/api/doctors?page=0&size=10"
```

### Get Doctor by ID
```bash
curl -X GET http://localhost:8080/api/doctors/1
```

### Update Doctor
```bash
curl -X PUT http://localhost:8080/api/doctors/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. Jane Smith",
    "specialization": "Neurology",
    "phone": "+250722777888",
    "location": {
      "id": 5
    }
  }'
```

### Delete Doctor
```bash
curl -X DELETE http://localhost:8080/api/doctors/1
```

---

## 4. USERS API (`/api/users`)

### Create User (requires location ID from Step 1)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin_user",
    "password": "SecurePassword123",
    "role": "ADMIN",
    "location": {
      "id": 5
    }
  }'
```

### Get All Users (Paginated)
```bash
curl -X GET "http://localhost:8080/api/users?page=0&size=10"
```

### Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1
```

### Search Users by Province Name
```bash
curl -X GET "http://localhost:8080/api/users/search?provinceName=Kigali City"
```

### Search Users by Province Code
```bash
curl -X GET "http://localhost:8080/api/users/search?provinceCode=KC"
```

### Update User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin_user",
    "password": "NewSecurePassword456",
    "role": "DOCTOR",
    "location": {
      "id": 5
    }
  }'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

---

## 5. APPOINTMENTS API (`/api/appointments`)

### Create Appointment
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

### Get All Appointments (Paginated)
```bash
curl -X GET "http://localhost:8080/api/appointments?page=0&size=10"
```

### Get Appointment by ID
```bash
curl -X GET http://localhost:8080/api/appointments/1
```

### Update Appointment
```bash
curl -X PUT http://localhost:8080/api/appointments/1 \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentDate": "2026-03-22",
    "reason": "Follow-up Checkup",
    "status": "COMPLETED",
    "patient": {
      "id": 1
    },
    "doctor": {
      "id": 1
    },
    "location": {
      "id": 5
    }
  }'
```

### Delete Appointment
```bash
curl -X DELETE http://localhost:8080/api/appointments/1
```

---

## 6. MEDICAL RECORDS API (`/api/medical-records`)

### Create Medical Record
```bash
curl -X POST http://localhost:8080/api/medical-records \
  -H "Content-Type: application/json" \
  -d '{
    "recordDate": "2026-03-11",
    "diagnosis": "Hypertension",
    "treatment": "Prescribed Lisinopril 10mg",
    "notes": "Patient advised to reduce sodium intake and exercise regularly",
    "patient": {
      "id": 1
    }
  }'
```

### Get All Medical Records (Paginated)
```bash
curl -X GET "http://localhost:8080/api/medical-records?page=0&size=10"
```

### Get Medical Record by ID
```bash
curl -X GET http://localhost:8080/api/medical-records/1
```

### Update Medical Record
```bash
curl -X PUT http://localhost:8080/api/medical-records/1 \
  -H "Content-Type: application/json" \
  -d '{
    "recordDate": "2026-03-11",
    "diagnosis": "Hypertension Stage 2",
    "treatment": "Prescribed Lisinopril 20mg",
    "notes": "Patient showing improvement, continue medication",
    "patient": {
      "id": 1
    }
  }'
```

### Delete Medical Record
```bash
curl -X DELETE http://localhost:8080/api/medical-records/1
```

---

## RECOMMENDED TESTING SEQUENCE

### Step 1️⃣: Create Location (Get Village ID)
```bash
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "KC",
    "provinceName": "Kigali City",
    "district": "Gasabo",
    "sector": "Ndera",
    "cell": "Nyarugenge",
    "village": "Kigali Central",
    "hospitalName": "Central Hospital Kigali"
  }'
```
**→ Save the returned `id` (typically 5)**

---

### Step 2️⃣: Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "nationalId": "1234567890123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+250722123456",
    "dateOfBirth": "1990-05-15"
  }'
```
**→ Save the returned patient `id` (typically 1)**

---

### Step 3️⃣: Create Doctor (Use Village ID from Step 1)
```bash
curl -X POST http://localhost:8080/api/doctors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dr. Jane Smith",
    "specialization": "Cardiology",
    "phone": "+250722456789",
    "location": {
      "id": 5
    }
  }'
```
**→ Save the returned doctor `id` (typically 1)**

---

### Step 4️⃣: Create User (Use Village ID from Step 1)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "kigali_admin",
    "password": "Password123",
    "role": "ADMIN",
    "location": {
      "id": 5
    }
  }'
```

---

### Step 5️⃣: Test Province Search Query ⭐ IMPORTANT
```bash
curl -X GET "http://localhost:8080/api/users/search?provinceName=Kigali City"
```
**Expected Result:** Should return the user created in Step 4

**OR by Province Code:**
```bash
curl -X GET "http://localhost:8080/api/users/search?provinceCode=KC"
```

---

### Step 6️⃣: Create Appointment (Use IDs from previous steps)
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "appointmentDate": "2026-03-20T14:30:00",
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
  }'
```

---

### Step 7️⃣: Create Medical Record (Use Patient ID from Step 2)
```bash
curl -X POST http://localhost:8080/api/medical-records \
  -H "Content-Type: application/json" \
  -d '{
    "recordDate": "2026-03-11T10:00:00",
    "diagnosis": "Hypertension",
    "treatment": "Prescribed Lisinopril 10mg",
    "notes": "Patient advised to reduce sodium intake",
    "patient": {
      "id": 1
    }
  }'
```

---

## QUICK TEST IN POWERSHELL

### Test All Endpoints Exist
```powershell
$baseUrl = "http://localhost:8080"
$endpoints = @(
    "GET /api/locations",
    "GET /api/patients",
    "GET /api/doctors",
    "GET /api/users",
    "GET /api/appointments",
    "GET /api/medical-records"
)

foreach ($endpoint in $endpoints) {
    $method, $path = $endpoint.Split(" ")
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl$path" -Method $method -UseBasicParsing
        Write-Host "✓ $endpoint - Status: $($response.StatusCode)"
    } catch {
        Write-Host "✗ $endpoint - Error: $($_.Exception.Message)"
    }
}
```

---

## HTTP STATUS CODES

| Code | Meaning | Example |
|------|---------|---------|
| **200** | OK | GET request successful |
| **201** | Created | POST request successful |
| **204** | No Content | DELETE successful |
| **400** | Bad Request | Missing required fields |
| **404** | Not Found | Resource doesn't exist |
| **409** | Conflict | Duplicate unique field (National ID, Username) |
| **500** | Server Error | Database or application error |

---

## IMPORTANT NOTES

✅ **Pagination Parameters** (for all GET requests):
```
?page=0&size=10
```
- `page`: 0-based page number (default: 0)
- `size`: Records per page (default: 10)

✅ **Date Format**: Use ISO 8601 format
```
2026-03-20          (for date only)
2026-03-20T14:30:00 (for date and time)
```

✅ **Required Fields**:
- **Patient**: nationalId (must be unique)
- **User**: username (must be unique)
- **All relations**: Provide `{"id": X}` format

✅ **Test Province Queries** to verify location hierarchy:
```bash
# These should return users if location hierarchy is working
GET /api/users/search?provinceName=Kigali City
GET /api/users/search?provinceCode=KC
```

---

## ADVANCED EXAMPLES

### Create Multiple Locations
```bash
# Northern Province
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "NK",
    "provinceName": "Northern Province",
    "district": "Musanze",
    "sector": "Muhoza",
    "cell": "Mugongo",
    "village": "Musanze Town",
    "hospitalName": "Musanze District Hospital"
  }'

# Western Province
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "WK",
    "provinceName": "Western Province",
    "district": "Karongi",
    "sector": "Gitarama",
    "cell": "Mukarange",
    "village": "Karongi Town",
    "hospitalName": "Karongi District Hospital"
  }'
```

### Pagination Examples
```bash
# Page 1, 5 items per page
curl -X GET "http://localhost:8080/api/patients?page=1&size=5"

# Page 2, 20 items per page
curl -X GET "http://localhost:8080/api/doctors?page=2&size=20"
```

---

## USING POSTMAN

1. **Import Collection**: Use `Medilink_HMS_API.postman_collection.json`
2. **Set base URL**: `http://localhost:8080`
3. **Test endpoints** in the order above
4. **Verify province search** returns correct users

---

**All endpoints are ready to test! Start with Step 1 and follow the sequence for best results.** 🚀
