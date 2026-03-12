🏥 Locations (contains full hierarchy)
// 1. Kigali Central Hospital
{
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Gasabo",
  "sector": "Ndera",
  "cell": "Nyarugenge",
  "village": "Kigali Central",
  "hospitalName": "Central Hospital Kigali"
}

// 2. Huye District Hospital
{
  "provinceCode": "S",
  "provinceName": "Southern Province",
  "district": "Huye",
  "sector": "Ngoma",
  "cell": "Nyarugunga",
  "village": "Huye Town",
  "hospitalName": "Huye District Hospital"
}

// 3. Musanze Referral
{
  "provinceCode": "N",
  "provinceName": "Northern Province",
  "district": "Musanze",
  "sector": "Kimironko",
  "cell": "Nyarutovu",
  "village": "Musanze VOI",
  "hospitalName": "Musanze Referral Hospital"
}

// 4. Rubavu Health Centre
{
  "provinceCode": "W",
  "provinceName": "Western Province",
  "district": "Rubavu",
  "sector": "Nyundo",
  "cell": "Cyanzarwe",
  "village": "Rubavu Town",
  "hospitalName": "Rubavu Health Centre"
}

// 5. Rwanda Military Hospital
{
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Kicukiro",
  "sector": "Kigali",
  "cell": "Gatsata",
  "village": "Kanombe",
  "hospitalName": "Rwanda Military Hospital"
}

How to use:
POST http://localhost:8080/api/locations with one of the above bodies.
Then GET /api/locations?page=0&size=5&sort=hospitalName,asc will list them.


👩‍⚕️ Users
(make sure corresponding location.id exists from the locations you created)


// 1. admin user
{
  "username": "admin01",
  "password": "pass123",
  "role": "ADMIN",
  "location": { "id": 1 }
}
// 2. Dr. Alice
{
  "username": "alice",
  "password": "pwd456",
  "role": "DOCTOR",
  "location": { "id": 2 }
}
// 3. Receptionist Paul
{
  "username": "paulr",
  "password": "paul789",
  "role": "RECEPTIONIST",
  "location": { "id": 3 }
}
// 4. Dr. Brian
{
  "username": "brian",
  "password": "brian321",
  "role": "DOCTOR",
  "location": { "id": 4 }
}
// 5. Nurse Jane
{
  "username": "janeN",
  "password": "jane654",
  "role": "RECEPTIONIST",
  "location": { "id": 5 }
}

🧑‍🤝‍🧑 Patients

// 1. John Doe
{
  "nationalId": "12345678901",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+250788000001",
  "dateOfBirth": "1980-01-15"
}
// 2. Mary Smith
{
  "nationalId": "10987654321",
  "firstName": "Mary",
  "lastName": "Smith",
  "phone": "+250788000002",
  "dateOfBirth": "1990-05-30"
}
// 3. Paul Kagame
{
  "nationalId": "11223344556",
  "firstName": "Paul",
  "lastName": "Kagame",
  "phone": "+250788000003",
  "dateOfBirth": "1975-08-20"
}
// 4. Alice Uwizeye
{
  "nationalId": "66778899001",
  "firstName": "Alice",
  "lastName": "Uwizeye",
  "phone": "+250788000004",
  "dateOfBirth": "2000-12-10"
}
// 5. Jean Pierre
{
  "nationalId": "22334455667",
  "firstName": "Jean",
  "lastName": "Pierre",
  "phone": "+250788000005",
  "dateOfBirth": "1985-03-05"
}


👨‍⚕️ Doctors

// 1. Dr. Emmanuel, cardiologist at location 1
{
  "name": "Emmanuel",
  "specialization": "Cardiology",
  "phone": "+250788100001",
  "location": { "id": 1 }
}
// 2. Dr. Rose, pediatrics at location 2
{
  "name": "Rose",
  "specialization": "Pediatrics",
  "phone": "+250788100002",
  "location": { "id": 2 }
}
// 3. Dr. Marc, surgery at location 3
{
  "name": "Marc",
  "specialization": "General Surgery",
  "phone": "+250788100003",
  "location": { "id": 3 }
}
// 4. Dr. Esther, neurology at location 4
{
  "name": "Esther",
  "specialization": "Neurology",
  "phone": "+250788100004",
  "location": { "id": 4 }
}
// 5. Dr. Kelvin, orthopedics at location 5
{
  "name": "Kelvin",
  "specialization": "Orthopedics",
  "phone": "+250788100005",
  "location": { "id": 5 }
}

📅 Appointments
(assumes patient id 1–5 and doctor id 1–5; adjust accordingly)

// 1. John with Emmanuel
{
  "appointmentDate": "2026-04-01T09:00:00",
  "status": "SCHEDULED",
  "patient": { "id": 1 },
  "doctor": { "id": 1 },
  "location": { "id": 1 }
}
// 2. Mary with Rose
{
  "appointmentDate": "2026-04-02T10:30:00",
  "status": "SCHEDULED",
  "patient": { "id": 2 },
  "doctor": { "id": 2 },
  "location": { "id": 2 }
}
// 3. Paul with Marc
{
  "appointmentDate": "2026-04-03T08:15:00",
  "status": "SCHEDULED",
  "patient": { "id": 3 },
  "doctor": { "id": 3 },
  "location": { "id": 3 }
}
// 4. Alice with Esther
{
  "appointmentDate": "2026-04-04T11:00:00",
  "status": "SCHEDULED",
  "patient": { "id": 4 },
  "doctor": { "id": 4 },
  "location": { "id": 4 }
}
// 5. Jean with Kelvin
{
  "appointmentDate": "2026-04-05T14:00:00",
  "status": "SCHEDULED",
  "patient": { "id": 5 },
  "doctor": { "id": 5 },
  "location": { "id": 5 }
}


📝 Medical Records


// 1. Record for patient 1
{
  "diagnosis": "Hypertension",
  "treatment": "Medication A",
  "notes": "Monitor blood pressure",
  "recordDate": "2026-03-01T10:00:00",
  "patient": { "id": 1 }
}
// 2. Record for patient 2
{
  "diagnosis": "Asthma",
  "treatment": "Inhaler",
  "notes": "Avoid allergens",
  "recordDate": "2026-03-02T11:00:00",
  "patient": { "id": 2 }
}
// 3. Record for patient 3
{
  "diagnosis": "Appendicitis",
  "treatment": "Surgery",
  "notes": "Post-op recovery",
  "recordDate": "2026-03-03T12:00:00",
  "patient": { "id": 3 }
}
// 4. Record for patient 4
{
  "diagnosis": "Migraine",
  "treatment": "Pain relief",
  "notes": "Keep headache diary",
  "recordDate": "2026-03-04T13:00:00",
  "patient": { "id": 4 }
}
// 5. Record for patient 5
{
  "diagnosis": "Fracture",
  "treatment": "Cast",
  "notes": "Physical therapy later",
  "recordDate": "2026-03-05T14:00:00",
  "patient": { "id": 5 }
}


🔄 Testing Tips
Insert Locations first – use their id in subsequent payloads.
For pagination tests, use GET /api/{entity}?page=0&size=3&sort=id,desc.
Use PUT /api/{entity}/{id} with a modified JSON to verify updates.
Call /api/users/search?provinceName=Kigali City or ?provinceCode=KC to check the custom query.
Once data exists, DELETE /api/{entity}/{id} should return 204.
With these five distinct records per endpoint, you'll confidently exercise every route and demonstrate all features. Happy testing!
