# Medilink HMS - Complete Database Structure

## Overview
This document describes the complete database structure for the Medilink Hospital Management System, following the **Hierarchical Location Design (Adjacency List Model)**.

---

## 1. LOCATIONS Table (Hierarchical Structure)

**Purpose**: Stores all administrative levels in ONE table using self-referencing hierarchy

```sql
CREATE TABLE locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(50),                    -- Province code (e.g., "KC" for Kigali City)
    name VARCHAR(100) NOT NULL,          -- Location name at any level
    location_type VARCHAR(50) NOT NULL,  -- PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    hospital_name VARCHAR(200),          -- Only for VILLAGE level
    parent_id BIGINT,                    -- Self-reference to parent location
    FOREIGN KEY (parent_id) REFERENCES locations(id)
);
```

**Hierarchy Example**:
```
Kigali City (Province, parent_id=NULL)
  └─ Gasabo (District, parent_id=1)
      └─ Remera (Sector, parent_id=2)
          └─ Rukiri (Cell, parent_id=3)
              └─ Rukiri I (Village, parent_id=4)
```

**Key Features**:
- ✅ Single table for all location levels
- ✅ Self-referencing with `parent_id`
- ✅ Enum for `location_type` ensures data consistency
- ✅ Tree structure: Province → District → Sector → Cell → Village
- ✅ Navigate up: Village → Cell → Sector → District → Province

---

## 2. PATIENTS Table

```sql
CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    national_id VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);
```

**Relationships**:
- One Patient → Many Medical Records (1:M)
- Many Patients ↔ Many Doctors (M:M through appointments)

---

## 3. DOCTORS Table

```sql
CREATE TABLE doctors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    location_id BIGINT NOT NULL,
    FOREIGN KEY (location_id) REFERENCES locations(id)
);
```

**Relationships**:
- Many Doctors → One Location (M:1) - Doctors work at a Village
- Many Doctors ↔ Many Patients (M:M through appointments)

---

## 4. USERS Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    location_id BIGINT NOT NULL,
    user_profile_id BIGINT UNIQUE,
    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (user_profile_id) REFERENCES user_profiles(id)
);
```

**Relationships**:
- One User → One UserProfile (1:1)
- Many Users → One Location (M:1) - Users belong to a Village

**EXAM CRITICAL**: Search users by Province
```java
// Navigate up the hierarchy: Village → Cell → Sector → District → Province
SELECT u FROM User u 
WHERE u.location.parent.parent.parent.parent.name = 'Kigali City'
```

---

## 5. USER_PROFILES Table

```sql
CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(150),
    alt_phone VARCHAR(20)
);
```

**Relationships**:
- One UserProfile → One User (1:1)

---

## 6. APPOINTMENTS Table

```sql
CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(255) NOT NULL,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);
```

**Relationships**:
- Many Appointments → One Patient (M:1)
- Many Appointments → One Doctor (M:1)
- Many Appointments → One Location (M:1)

---

## 7. MEDICAL_RECORDS Table

```sql
CREATE TABLE medical_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    diagnosis VARCHAR(1000) NOT NULL,
    treatment VARCHAR(1000) NOT NULL,
    record_date DATE NOT NULL,
    notes VARCHAR(2000),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);
```

**Relationships**:
- Many Medical Records → One Patient (M:1)

---

## 8. DOCTOR_PATIENT Table (Join Table for M:M)

```sql
CREATE TABLE doctor_patient (
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);
```

**Purpose**: Implements Many-to-Many relationship between Doctors and Patients

---

## Complete Relationship Summary

### One-to-One (1:1)
- User ↔ UserProfile

### One-to-Many (1:M)
- Patient → Medical Records
- Location → Users
- Location → Doctors
- Location → Appointments
- Patient → Appointments
- Doctor → Appointments

### Many-to-One (M:1)
- Users → Location (Village)
- Doctors → Location (Village)
- Appointments → Patient
- Appointments → Doctor
- Appointments → Location
- Medical Records → Patient

### Many-to-Many (M:M)
- Doctors ↔ Patients (through doctor_patient join table)

### Self-Referencing (Hierarchical)
- Location → Location (parent-child relationship)

---

## Sample Data Structure

### Locations (10 records in hierarchy)
```
1. Kigali City (Province)
2. ├─ Gasabo (District)
3. │  └─ Remera (Sector)
4. │     └─ Rukiri (Cell)
5. │        └─ Rukiri I (Village) ← Users/Doctors linked here
6. Northern Province (Province)
7. ├─ Musanze (District)
8. │  └─ Muhoza (Sector)
9. │     └─ Cyuve (Cell)
10.│        └─ Cyuve Village (Village) ← Users/Doctors linked here
```

### Other Tables
- 3 Patients
- 3 Doctors (linked to Villages)
- 3 Users with Profiles (linked to Villages)
- 3 Appointments
- 3 Medical Records

---

## Key Design Patterns Used

1. **Adjacency List Model**: For hierarchical location data
2. **Tree Data Structure**: Province as root, branches down to Village
3. **Join Table Pattern**: For Many-to-Many relationships
4. **Cascade Operations**: Parent deletion cascades to children
5. **Lazy Loading**: Prevents N+1 query problems

---

## EXAM Requirements Met

✅ Single `locations` table with self-referencing hierarchy
✅ All relationship types: 1:1, 1:M, M:1, M:M, Self-referencing
✅ Pagination support on all endpoints
✅ existsBy() validation methods
✅ Province search by name and code (navigating up hierarchy)
✅ Enum for LocationType
✅ Automatic data initialization on startup

---

## How to Reset Database

The application uses `spring.jpa.hibernate.ddl-auto=create-drop`:
- On startup: Drops all tables → Creates new tables → Inserts sample data
- On shutdown: Drops all tables

To keep data between restarts, change to:
```properties
spring.jpa.hibernate.ddl-auto=update
```
