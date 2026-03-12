# Location Endpoint - Quick Reference

## Understanding the Self-Referencing Location Table

### Structure
```
Single "locations" table with hierarchical relationships:
Province (parent_location_id = null)
  └─ District (parent_location_id = Province ID)
      └─ Sector (parent_location_id = District ID)
          └─ Cell (parent_location_id = Sector ID)
              └─ Village (parent_location_id = Cell ID)
```

---

## Creating Location Hierarchy

### Endpoint
```
POST /api/locations
Content-Type: application/json
```

### Request Body - LocationDTO
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

### Field Descriptions
| Field | Level | Meaning |
|-------|-------|---------|
| `provinceCode` | Province | Two-letter code (e.g., "KC" for Kigali City) |
| `provinceName` | Province | Full name (e.g., "Kigali City") |
| `district` | District | District name (e.g., "Gasabo") |
| `sector` | Sector | Sector name (e.g., "Ndera") |
| `cell` | Cell | Cell name (e.g., "Nyarugenge") |
| `village` | Village | Village name (e.g., "Kigali Central") |
| `hospitalName` | Village | Hospital/Health center name |

### Response - Village Entity
```json
{
  "id": 5,
  "locationLevel": "VILLAGE",
  "code": null,
  "name": "Kigali Central",
  "hospitalName": "Central Hospital Kigali",
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Gasabo",
  "sector": "Ndera",
  "cell": "Nyarugenge",
  "village": "Kigali Central",
  "parentLocationId": 4,
  "parentLocation": {
    "id": 4,
    "name": "Nyarugenge",
    ...
  }
}
```

### Key Points
✅ **One POST creates 5 records:**
- 1st call: Creates Province if it doesn't exist
- 2nd level: Creates District linked to Province
- 3rd level: Creates Sector linked to District
- 4th level: Creates Cell linked to Sector
- 5th level: Creates Village linked to Cell (RETURNED)

✅ **Returns: The Village entity** (the deepest level)

✅ **Use Village ID:** This ID (e.g., 5) is what you reference when creating:
- Doctors: `"location": { "id": 5 }`
- Users: `"location": { "id": 5 }`
- Appointments: `"location": { "id": 5 }`

---

## Testing Examples

### Example 1: Create Kigali City Hierarchy
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

### Example 2: Create Northern Province Hierarchy
```json
{
  "provinceCode": "NK",
  "provinceName": "Northern Province",
  "district": "Musanze",
  "sector": "Muhoza",
  "cell": "Gicumbi",
  "village": "Ruhengeri",
  "hospitalName": "Muhoza Health Center"
}
```

### Example 3: Create Western Province Hierarchy
```json
{
  "provinceCode": "WK",
  "provinceName": "Western Province",
  "district": "Karongi",
  "sector": "Gitarama",
  "cell": "Mukarange",
  "village": "Karongi Town",
  "hospitalName": "Karongi District Hospital"
}
```

---

## Retrieving Location Data

### Get All Locations (Paginated)
```
GET /api/locations?page=0&size=10
```

### Get Specific Location
```
GET /api/locations/{id}
```
**Example:** `GET /api/locations/5` - Returns Village details with all parent relationships

### Get Location Hierarchy (from Village)
The response includes the complete chain:
```
Village (id=5)
  ├─ parentLocation: Cell (id=4)
  │   ├─ parentLocation: Sector (id=3)
  │   │   ├─ parentLocation: District (id=2)
  │   │   │   └─ parentLocation: Province (id=1)
```

---

## Updating Location
```
PUT /api/locations/{id}
Content-Type: application/json
```

```json
{
  "provinceCode": "KC",
  "provinceName": "Kigali City",
  "district": "Gasabo",
  "sector": "Ndera",
  "cell": "Nyarugenge",
  "village": "Kigali Central UPDATED",
  "hospitalName": "Central Hospital Kigali - New Building"
}
```

---

## Deleting Location
```
DELETE /api/locations/{id}
```

**Warning:** Deleting a parent location may cascade delete its children depending on your database configuration.

---

## Relationship Queries (From Users)

Once locations are created and users are linked to Village locations, you can query users by province:

### Search by Province Name
```
GET /api/users/search?provinceName=Kigali City
```

**Returns:** All users whose location (Village) chain includes "Kigali City" as the province

### Search by Province Code
```
GET /api/users/search?provinceCode=KC
```

**Returns:** All users whose location (Village) chain includes "KC" as the province code

---

## Rwanda Administrative Hierarchy Reference

**Full Example Hierarchy:**
```
Province: Kigali City (Code: KC)
  ├─ District: Gasabo
  │   ├─ Sector: Ndera
  │   │   └─ Cell: Nyarugenge
  │   │       └─ Village: Kigali Central
  │   ├─ Sector: Kimironko
  │   │   └─ Cell: Kibaya
  │   │       └─ Village: Kibaya Market
  │
  ├─ District: Kicukiro
  │   ├─ Sector: Remera
  │   │   └─ Cell: Rebero
  │   │       └─ Village: Rebero Heights

Province: Northern Province (Code: NK)
  └─ District: Musanze
      └─ Sector: Muhoza
          └─ Cell: Mugongo
              └─ Village: Musanze Town
```

---

## ⚡ Pro Tips

1. **Always create Location first** before creating Users, Doctors, or Appointments
2. **Save the returned Village ID** from the POST response
3. **Use that ID** for all subsequent entity creations
4. **Test province queries** to verify your hierarchy is working
5. **Use Postman** to easily manage test data and verify relationships
