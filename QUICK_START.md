# QUICK START GUIDE
## Roja Performance - Phase 1 Backend

---

## 🚀 5-Minute Setup

### Step 1: Prerequisites Installation
```bash
# Java 17+
java -version

# MySQL (should be running)
mysql --version

# Maven (should be in PATH)
mvn --version
```

### Step 2: Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=root
```

### Step 3: Build Project
```bash
cd Roja-Performance
./mvnw clean compile
```

### Step 4: Run Application
```bash
./mvnw spring-boot:run
```

### Step 5: Verify
```bash
curl http://localhost:8080/api/v1/tyres/health/status
```

✅ **Done!** API is running on `http://localhost:8080`

---

## 📱 API Quick Reference

### Create Tyre
```bash
curl -X POST http://localhost:8080/api/v1/tyres \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 4500.00,
    "stockQuantity": 50,
    "description": "All-season tyre"
  }'
```

### Get All Tyres
```bash
curl http://localhost:8080/api/v1/tyres
```

### Get Tyre by ID
```bash
curl http://localhost:8080/api/v1/tyres/1
```

### Get by Brand
```bash
curl http://localhost:8080/api/v1/tyres/brand/Michelin
```

### Get Available (Stock > 0)
```bash
curl http://localhost:8080/api/v1/tyres/available/list
```

### Update Tyre
```bash
curl -X PUT http://localhost:8080/api/v1/tyres/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 5000.00,
    "stockQuantity": 45,
    "description": "Updated tyre"
  }'
```

### Delete Tyre
```bash
curl -X DELETE http://localhost:8080/api/v1/tyres/1
```

### Update Stock (+10 units)
```bash
curl -X PUT "http://localhost:8080/api/v1/tyres/1/stock?quantity=10"
```

### Reduce Stock (-5 units)
```bash
curl -X POST "http://localhost:8080/api/v1/tyres/1/reduce-stock?quantity=5"
```

---

## 🗂️ Project Structure Overview

```
Roja-Performance/
├── src/main/java/com/tyreshop/Roja/Performance/
│   ├── controller/TyreController.java          ← REST endpoints
│   ├── service/TyreService.java                ← Interface
│   ├── service/impl/TyreServiceImpl.java        ← Business logic
│   ├── repository/TyreRepository.java          ← Data access
│   ├── entity/Tyre.java                        ← Database entity
│   ├── dto/TyreRequestDTO.java                 ← Request DTO
│   ├── dto/TyreResponseDTO.java                ← Response DTO
│   ├── exception/ResourceNotFoundException.java ← Custom exception
│   └── exception/GlobalExceptionHandler.java   ← Error handling
│
├── src/main/resources/
│   ├── application.properties                  ← Configuration
│   └── static/index.html                       ← Web dashboard
│
├── pom.xml                                     ← Maven config
├── PHASE1_README.md                            ← Full documentation
└── QUICK_START.md                              ← This file
```

---

## 🔍 Key Classes

### TyreController
- **Location:** `controller/TyreController.java`
- **Purpose:** REST API endpoints
- **Methods:** 12 endpoints for CRUD operations
- **Base URL:** `/api/v1/tyres`

### TyreService
- **Location:** `service/TyreService.java`
- **Purpose:** Business logic interface
- **Methods:** 18 service methods

### TyreServiceImpl
- **Location:** `service/impl/TyreServiceImpl.java`
- **Purpose:** Service implementation
- **Features:** Validation, error handling, logging

### TyreRepository
- **Location:** `repository/TyreRepository.java`
- **Purpose:** Database access
- **Methods:** 6 custom query methods

### Tyre Entity
- **Location:** `entity/Tyre.java`
- **Fields:** id, brand, size, price, stockQuantity, description
- **Annotations:** @Entity, @Table, validation annotations

---

## 🧪 Using Postman

### Import Collection
1. Open Postman
2. Create new request:
   - Method: POST
   - URL: `http://localhost:8080/api/v1/tyres`
   - Headers: `Content-Type: application/json`
   - Body:
     ```json
     {
       "brand": "Michelin",
       "size": "195/65R15",
       "price": 4500.00,
       "stockQuantity": 50,
       "description": "All-season tyre"
     }
     ```

---

## 📝 Sample Requests

### Request 1: Create Multiple Tyres
```json
[
  {
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 4500.00,
    "stockQuantity": 50,
    "description": "All-season comfort tyre"
  },
  {
    "brand": "Bridgestone",
    "size": "215/60R16",
    "price": 5200.00,
    "stockQuantity": 40,
    "description": "Premium all-season tyre"
  }
]
```

### Request 2: Price Range Query
```
GET /api/v1/tyres/price?minPrice=4000&maxPrice=6000
```

### Request 3: Stock Management
```
# Add stock
PUT /api/v1/tyres/1/stock?quantity=20

# Reduce stock
POST /api/v1/tyres/1/reduce-stock?quantity=5
```

---

## 🐛 Troubleshooting

### Issue: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection error
**Solution:** Ensure:
1. MySQL is running: `mysql -u root -p`
2. Credentials are correct in `application.properties`
3. Database permission: `GRANT ALL ON tyreshop.* TO 'root'@'localhost';`

### Issue: Build fails with dependency error
**Solution:** Update Maven cache:
```bash
./mvnw clean install -U
```

### Issue: Classes not found
**Solution:** Ensure Java 17+ is installed:
```bash
java -version
# Should show 17 or higher
```

---

## 🔑 Important Endpoints

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/v1/tyres` | GET | Get all tyres |
| `/api/v1/tyres` | POST | Create tyre |
| `/api/v1/tyres/{id}` | GET | Get single tyre |
| `/api/v1/tyres/{id}` | PUT | Update tyre |
| `/api/v1/tyres/{id}` | DELETE | Delete tyre |
| `/api/v1/tyres/available/list` | GET | Available tyres |
| `/api/v1/tyres/brand/{brand}` | GET | By brand |
| `/api/v1/tyres/size/{size}` | GET | By size |
| `/api/v1/tyres/price` | GET | By price range |

---

## 💾 Database Basics

### Access MySQL
```bash
mysql -u root -p
use tyreshop;
SELECT * FROM tyres;
```

### View Tyres Table
```sql
DESCRIBE tyres;
```

### Sample Queries
```sql
-- Get all tyres
SELECT * FROM tyres;

-- Get by brand
SELECT * FROM tyres WHERE brand = 'Michelin';

-- Count available
SELECT COUNT(*) FROM tyres WHERE stock_quantity > 0;

-- Check latest
SELECT * FROM tyres ORDER BY created_at DESC LIMIT 1;
```

---

## 📊 Response Examples

### Success Response
```json
{
  "success": true,
  "message": "Tyre created successfully",
  "data": {
    "id": 1,
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 4500.00,
    "stockQuantity": 50,
    "description": "All-season tyre",
    "createdAt": 1704067200000,
    "updatedAt": 1704067200000
  },
  "timestamp": 1704067200000
}
```

### Error Response
```json
{
  "success": false,
  "message": "Tyre not found with ID: 999",
  "data": null,
  "timestamp": 1704067200000
}
```

### Validation Error
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "brand": "Brand name is required",
    "price": "Price must be greater than zero"
  },
  "timestamp": 1704067200000
}
```

---

## 🎯 Common Tasks

### Add New Tyre
```bash
curl -X POST http://localhost:8080/api/v1/tyres \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Continental",
    "size": "205/55R16",
    "price": 5500.00,
    "stockQuantity": 40,
    "description": "Eco-friendly tyre"
  }'
```

### Check Inventory
```bash
curl "http://localhost:8080/api/v1/tyres/available/list"
```

### Update Price
```bash
curl -X PUT http://localhost:8080/api/v1/tyres/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 4800.00,
    "stockQuantity": 50,
    "description": "All-season tyre"
  }'
```

### Process Order (Reduce Stock)
```bash
curl -X POST "http://localhost:8080/api/v1/tyres/1/reduce-stock?quantity=5"
```

---

## 📖 Documentation Files

- **PHASE1_README.md** - Complete API reference
- **PHASE1_IMPLEMENTATION.md** - Implementation details
- **QUICK_START.md** - This file
- **README.md** - General information

---

## 🆘 Getting Help

1. **Check Logs**
   ```bash
   # Look at console output for errors
   # Check application.properties for logging.level
   ```

2. **Verify Build**
   ```bash
   ./mvnw clean compile
   ```

3. **Check Database**
   ```bash
   mysql -u root -p
   use tyreshop;
   SHOW TABLES;
   ```

4. **Test Endpoints**
   ```bash
   curl http://localhost:8080/api/v1/tyres/health/status
   ```

---

## ✅ Verification Checklist

- [ ] Java 17+ installed
- [ ] MySQL running
- [ ] Database configured
- [ ] Code compiled successfully
- [ ] Application started
- [ ] Health endpoint responds
- [ ] Can create tyre
- [ ] Can retrieve tyres
- [ ] Can update tyre
- [ ] Can delete tyre

---

## 🚀 Next Steps

1. ✅ Run the application
2. ✅ Test all endpoints
3. ✅ Review code structure
4. ✅ Check database
5. ⏳ Plan Phase 2 features

---

## 📞 Support

For issues or questions, refer to:
- PHASE1_README.md for detailed API docs
- PHASE1_IMPLEMENTATION.md for architecture
- Code comments for implementation details

---

**Happy Coding! 🎉**  
**Roja Performance - Phase 1 Backend**

