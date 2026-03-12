 # MediLink HMS - Hospital Management System

A comprehensive Spring Boot application for managing multiple hospitals across different provinces in Rwanda.

## 🎯 Project Overview

This system manages:
- Multiple hospitals across different provinces
- Patient identification using National ID (Digital ID simulation)
- Doctor appointments and medical records
- User roles (ADMIN, DOCTOR, RECEPTIONIST)

## 🛠️ Technology Stack

- **Java**: 21
- **Spring Boot**: 4.0.2
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Utilities**: Lombok
- **API**: REST

## 📁 Project Structure

```
medilink-HMS/
├── src/main/java/com/medilink/medilink/HMS/
│   ├── entity/          # 6 JPA entities with relationships
│   ├── repository/      # Data access layer
│   ├── service/         # Business logic layer
│   └── controller/      # REST API endpoints
├── src/main/resources/
│   └── application.properties
├── EXAM_DOCUMENTATION.md      # Complete exam answers (30 marks)
├── TEST_DATA_EXAMPLES.md      # Sample test data
├── EXAM_QUICK_REFERENCE.md    # Quick reference guide
└── README.md                  # This file
```

## 🗄️ Database Entities

1. **Location** - Hospital locations across provinces
2. **User** - System users with roles
3. **Patient** - Patient information with National ID
4. **Doctor** - Doctor information and specializations
5. **Appointment** - Patient-Doctor appointments
6. **MedicalRecord** - Patient medical history

## 🔗 Entity Relationships

- **Location → User** (OneToMany)
- **Location → Doctor** (OneToMany)
- **Location → Appointment** (OneToMany)
- **Doctor ↔ Patient** (ManyToMany via join table)
- **Patient → Appointment** (OneToMany)
- **Patient → MedicalRecord** (OneToMany)
- **Doctor → Appointment** (OneToMany)

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd medilink-HMS
```

2. **Create PostgreSQL database**
```sql
CREATE DATABASE medilink_db;
```

3. **Configure database credentials**

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

4. **Build the project**
```bash
./mvnw clean install
```

5. **Run the application**
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Location Endpoints
- `POST /api/locations` - Create location
- `GET /api/locations` - Get all locations
- `GET /api/locations/{id}` - Get location by ID
- `GET /api/locations/paginated?page=0&size=10&sortBy=id` - Paginated list
- `GET /api/locations/sorted?sortBy=provinceName` - Sorted list
- `PUT /api/locations/{id}` - Update location
- `DELETE /api/locations/{id}` - Delete location

### User Endpoints
- `POST /api/users` - Create user
- `GET /api/users` - Get all users
- `GET /api/users/by-province-name?provinceName=Kigali City` - By province name
- `GET /api/users/by-province-code?provinceCode=KC` - By province code
- `GET /api/users/paginated?page=0&size=10` - Paginated list

### Patient Endpoints
- `POST /api/patients` - Create patient (validates National ID)
- `GET /api/patients` - Get all patients
- `GET /api/patients/paginated?page=0&size=10` - Paginated list

### Doctor Endpoints
- `POST /api/doctors` - Create doctor
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/paginated?page=0&size=10` - Paginated list

### Appointment Endpoints
- `POST /api/appointments` - Create appointment
- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/paginated?page=0&size=10` - Paginated list

### Medical Record Endpoints
- `POST /api/medical-records` - Create medical record
- `GET /api/medical-records` - Get all medical records
- `GET /api/medical-records/paginated?page=0&size=10` - Paginated list

## 🧪 Testing

### Sample Request - Create Location
```bash
curl -X POST http://localhost:8080/api/locations \
  -H "Content-Type: application/json" \
  -d '{
    "provinceCode": "KC",
    "provinceName": "Kigali City",
    "district": "Gasabo",
    "hospitalName": "King Faisal Hospital"
  }'
```

### Sample Request - Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "nationalId": "1199780012345678",
    "firstName": "Jean",
    "lastName": "Mugabo",
    "phone": "+250788123456",
    "dateOfBirth": "1997-05-15"
  }'
```

### Sample Request - Query Users by Province
```bash
curl "http://localhost:8080/api/users/by-province-name?provinceName=Kigali%20City"
```

For more test examples, see `TEST_DATA_EXAMPLES.md`

## ✨ Key Features

### 1. Pagination
Efficiently handle large datasets by loading data in chunks:
```
GET /api/locations/paginated?page=0&size=10&sortBy=hospitalName
```

### 2. Sorting
Sort results by any field:
```
GET /api/locations/sorted?sortBy=provinceName
```

### 3. Validation
- Prevents duplicate National IDs for patients
- Prevents duplicate usernames for users
- Uses `existsByNationalId()` and `existsByUsername()` methods

### 4. Complex Relationships
- Many-to-Many: Doctors can treat multiple patients
- One-to-Many: Locations have multiple users, doctors, appointments
- Proper foreign key constraints and referential integrity

### 5. Province Queries
Query users by province name OR province code:
```
GET /api/users/by-province-name?provinceName=Kigali City
GET /api/users/by-province-code?provinceCode=KC
```

## 📚 Documentation

- **EXAM_DOCUMENTATION.md** - Complete documentation covering all 30 exam marks
- **TEST_DATA_EXAMPLES.md** - Sample JSON data for testing
- **EXAM_QUICK_REFERENCE.md** - Quick reference guide for exam

## 🏗️ Architecture

### Layered Architecture

1. **Entity Layer** - JPA entities with relationships
2. **Repository Layer** - Data access using Spring Data JPA
3. **Service Layer** - Business logic and validation
4. **Controller Layer** - REST API endpoints

### Design Patterns

- **Repository Pattern** - Data access abstraction
- **Service Pattern** - Business logic separation
- **DTO Pattern** - (Can be added for production)
- **Dependency Injection** - Using Spring's @Autowired

## 🔒 Security Note

This is an educational project. For production:
- Add Spring Security
- Implement JWT authentication
- Hash passwords using BCrypt
- Add input validation
- Implement proper error handling
- Add API rate limiting

## 📝 Database Schema

The application automatically creates the following tables:
- `locations`
- `users`
- `patients`
- `doctors`
- `appointments`
- `medical_records`
- `doctor_patient` (join table for Many-to-Many)

## 🤝 Contributing

This is an exam project. For educational purposes only.

## 📄 License

Educational project for AUCA midterm exam.

## 👨‍💻 Author

Created for MediLink HMS Midterm Exam

## 📞 Support

For questions about the implementation, refer to:
- Code comments in each file
- EXAM_DOCUMENTATION.md for detailed explanations
- EXAM_QUICK_REFERENCE.md for quick answers

## 🎓 Exam Coverage

This project covers all 30 marks:
- ✅ ERD with 5+ tables (3 marks)
- ✅ Saving Location (2 marks)
- ✅ Sorting & Pagination (5 marks)
- ✅ Many-to-Many relationship (3 marks)
- ✅ One-to-Many relationship (2 marks)
- ✅ One-to-One relationship (2 marks)
- ✅ existBy() method (2 marks)
- ✅ Query by province (4 marks)
- ✅ Viva-Voce questions (7 marks)

---

**Good luck with your exam! 🎓**
