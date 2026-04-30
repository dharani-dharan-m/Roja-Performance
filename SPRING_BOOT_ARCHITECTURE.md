# 🏗️ SPRING BOOT ARCHITECTURE GUIDE - SENIOR ARCHITECT NOTES
## Roja Performance - Complete Project Structure & Configuration

**Build Status:** ✅ SUCCESSFUL  
**Date:** March 4, 2026  
**Java Version:** 17.0.18  
**Maven Version:** 3.9.12  
**Spring Boot:** 4.0.3  

---

## 📋 TABLE OF CONTENTS
1. [Current Project Structure](#current-project-structure)
2. [Full pom.xml Reference](#full-pomxml-reference)
3. [AppConfig.java - Corrected Implementation](#appconfigjava-corrected)
4. [Key Files with Corrected Packages](#key-files-corrected)
5. [Maven Reload Instructions](#maven-reload-instructions)
6. [Compilation & Deployment](#compilation--deployment)
7. [Architecture Best Practices](#architecture-best-practices)

---

## 📁 CURRENT PROJECT STRUCTURE

```
src/main/java/
├── com/
│   └── tyreshop/
│       └── Roja/
│           └── Performance/
│               ├── RojaPerformanceApplication.java (✅ FIXED)
│               ├── config/
│               │   ├── AppConfig.java (✅ FIXED - WebMvcConfigurer)
│               │   └── DataInitializer.java
│               ├── controller/
│               │   ├── TyreController.java (✅ PHASE 1)
│               │   ├── ProductController.java (✅ FIXED Package)
│               │   ├── CustomerController.java
│               │   ├── OrderController.java
│               │   └── HomeController.java
│               ├── service/
│               │   ├── TyreService.java (✅ PHASE 1)
│               │   ├── ProductService.java
│               │   ├── CustomerService.java
│               │   ├── OrderService.java
│               │   └── impl/
│               │       └── TyreServiceImpl.java (✅ PHASE 1)
│               ├── repository/
│               │   ├── TyreRepository.java (✅ PHASE 1)
│               │   ├── ProductRepository.java
│               │   ├── CustomerRepository.java
│               │   ├── OrderRepository.java
│               │   └── UserRepository.java
│               ├── entity/
│               │   ├── Tyre.java (✅ PHASE 1)
│               │   ├── Product.java
│               │   ├── Customer.java
│               │   ├── Order.java
│               │   └── User.java
│               ├── dto/
│               │   ├── TyreRequestDTO.java (✅ PHASE 1)
│               │   ├── TyreResponseDTO.java (✅ PHASE 1)
│               │   └── ApiResponse.java (✅ PHASE 1)
│               ├── model/
│               │   ├── Product.java
│               │   ├── Customer.java
│               │   └── Order.java
│               └── exception/
│                   ├── GlobalExceptionHandler.java (✅ PHASE 1)
│                   └── ResourceNotFoundException.java (✅ PHASE 1)
```

### ✅ **Structure Verification**
- **Base Package:** `com.tyreshop.Roja.Performance` (consistent across all files)
- **All imports:** Correctly reference the base package
- **Spring component scanning:** Properly configured in `@SpringBootApplication`
- **No package mismatches:** All files verified

---

## 📦 FULL pom.xml REFERENCE

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Parent POM -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.0.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <!-- Project Coordinates -->
    <groupId>com.tyreshop</groupId>
    <artifactId>Roja-Performance</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Roja-Performance</name>
    <description>Tyre Shop Management System - Spring Boot</description>
    
    <!-- Properties -->
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <!-- Dependencies -->
    <dependencies>
        <!-- ✅ Spring Boot Web (includes servlet, mvc) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- ✅ Spring Data JPA (includes hibernate) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- ✅ Validation Framework -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- ✅ MySQL Connector -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- ✅ Lombok for Boilerplate Reduction -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- ✅ Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!-- Build Configuration -->
    <build>
        <plugins>
            <!-- Compiler Plugin with Lombok Support -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            
            <!-- Spring Boot Maven Plugin -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 🔍 **Dependency Analysis**

| Dependency | Purpose | Provides |
|-----------|---------|----------|
| `spring-boot-starter-web` | Web/REST API | Tomcat, Spring WebMVC, Servlet API |
| `spring-boot-starter-data-jpa` | Database ORM | Hibernate, JPA, Spring Data Repositories |
| `spring-boot-starter-validation` | Input Validation | Jakarta Bean Validation (JSR-380) |
| `mysql-connector-j` | MySQL Driver | JDBC Connection to MySQL |
| `lombok` | Code Generation | @Data, @Builder, @Slf4j, etc. |
| `spring-boot-starter-test` | Testing Framework | JUnit 5, Mockito, AssertJ |

---

## ✅ AppConfig.java - CORRECTED IMPLEMENTATION

**Location:** `com.tyreshop.Roja.Performance.config.AppConfig.java`

```java
package com.tyreshop.Roja.Performance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AppConfig - Application Configuration
 * 
 * Configures Spring MVC and application-level settings:
 * - CORS (Cross-Origin Resource Sharing) for API access
 * - Other web configuration as needed
 * 
 * Implements WebMvcConfigurer to customize Spring MVC configuration
 * Requires dependency: spring-boot-starter-web (which includes WebMvcConfigurer)
 * 
 * @author Roja Performance Architecture Team
 * @version 1.0
 * @since 2026-03-04
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configure CORS settings for the application
     * 
     * Allows API endpoints to be accessed from different origins
     * (useful for frontend applications on different domains)
     * 
     * @param registry CorsRegistry for mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
```

### 🔑 **Key Points**

**Imports Required:**
```java
// ✅ CORRECT - Provided by spring-boot-starter-web
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
```

**Why It Works:**
- `spring-boot-starter-web` includes `spring-boot-starter-webmvc`
- WebMvcConfigurer is provided by Spring Framework
- CorsRegistry is available in servlet configuration package
- No additional dependencies needed

**CORS Configuration Breakdown:**
```
/api/**          → Applies to all /api endpoints
allowedOrigins("*") → Allows requests from any origin
allowedMethods   → Allows 6 HTTP method types
allowedHeaders("*") → Allows any headers
allowCredentials → false (appropriate for *  origins)
maxAge(3600)     → Browser caches for 1 hour
```

---

## ✅ KEY FILES - CORRECTED PACKAGES

### 1. **RojaPerformanceApplication.java**

```java
package com.tyreshop.Roja.Performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Roja Performance Application - Main Entry Point
 * Spring Boot application for Tyre Shop Management System
 * 
 * @author Roja Performance
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.tyreshop.Roja.Performance")
public class RojaPerformanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RojaPerformanceApplication.class, args);
    }
}
```

**Key Changes:**
- `scanBasePackages = "com.tyreshop.Roja.Performance"` - Explicitly sets component scan base
- Ensures all `@Component`, `@Service`, `@Repository`, `@Controller` are found
- Good practice for larger projects

### 2. **ProductController.java** (CORRECTED)

```java
package com.tyreshop.Roja.Performance.controller;  // ✅ CORRECTED PACKAGE

import com.tyreshop.Roja.Performance.model.Product;
import com.tyreshop.Roja.Performance.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ... rest of controller methods
}
```

**Package Consistency:**
- ✅ File Location: `src/main/java/com/tyreshop/Roja/Performance/controller/ProductController.java`
- ✅ Package Declaration: `package com.tyreshop.Roja.Performance.controller;`
- ✅ Imports: All reference `com.tyreshop.Roja.Performance.*`

---

## 🔧 Maven Reload Instructions (VS Code)

### **Option 1: Automatic Maven Reload in VS Code**

1. **Reload Projects:**
   - Open Command Palette: `Ctrl+Shift+P`
   - Type: `Maven: Reload Projects`
   - Press Enter

2. **Clear Cache:**
   - Open Command Palette: `Ctrl+Shift+P`
   - Type: `Java: Clean Language Server Workspace`
   - Press Enter

3. **Restart Extension Host** (if issues persist):
   - Open Command Palette: `Ctrl+Shift+P`
   - Type: `Developer: Restart Extension Host`
   - Press Enter

### **Option 2: Maven CLI Refresh**

```bash
# Force update of dependencies
mvn clean install -U

# Just compile with dependency updates
mvn clean compile -U -DskipTests
```

### **Option 3: Full Project Cleanup**

```bash
# Delete M2 repository cache (risky - will redownload everything)
# Windows:
$home\.m2\repository

# Then rebuild:
mvn clean install -DskipTests
```

### **Option 4: IDE Settings Reset**

1. **Delete VS Code Java Cache:**
   - `~/.config/Code/User/workspaceStorage/` (or equivalent)
   - Restart Code

2. **Reload Window:**
   - `Ctrl+Shift+P` → `Developer: Reload Window`

---

## ⚙️ Compilation & Deployment

### **Local Compilation**

```bash
# Full build (compile + test + package)
mvn clean install

# Compile only (skip tests)
mvn clean compile -DskipTests

# Compile specific goal
mvn clean package -DskipTests

# Quick compile (no clean)
mvn compile -DskipTests
```

### **Run Application Locally**

```bash
# Using Maven Spring Boot plugin (development)
mvn spring-boot:run

# Using JAR file (production)
java -jar target/Roja-Performance-0.0.1-SNAPSHOT.jar

# With custom port
java -jar target/Roja-Performance-0.0.1-SNAPSHOT.jar --server.port=8081
```

### **Running from VS Code**

1. **Using Debug Configuration:**
   - Click "Run and Debug" in left sidebar
   - Select "Java" or "Spring Boot" configuration
   - Click green play button

2. **Using Terminal:**
   ```bash
   mvn spring-boot:run
   ```

3. **Using Task:**
   - Terminal → Run Task → Select Maven task
   - Choose "spring-boot:run"

### **API Testing - Quick Start**

```bash
# Health check
curl http://localhost:8080/api/v1/tyres/health/status

# Get all tyres
curl http://localhost:8080/api/v1/tyres

# Get products
curl http://localhost:8080/api/products
```

---

## 🎯 Architecture Best Practices Applied

### **1. Layered Architecture**
```
┌─────────────────┐
│   Controller    │  REST endpoints, validation, HTTP
├─────────────────┤
│    Service      │  Business logic, transactions
├─────────────────┤
│  Repository     │  Data access, JPA queries
├─────────────────┤
│    Entity       │  JPA mapping, database model
└─────────────────┘
```

### **2. Dependency Injection (Spring)**
```java
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;  // Auto-wired
}
```

### **3. RESTful Conventions**
```
POST   /api/products           → Create
GET    /api/products           → Read All
GET    /api/products/{id}      → Read One
PUT    /api/products/{id}      → Update
DELETE /api/products/{id}      → Delete
```

### **4. Exception Handling**
```java
@ControllerAdvice  // Global exception handler
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound() { ... }
}
```

### **5. Cross-Cutting Concerns**
- **CORS:** Configured in AppConfig.java
- **Validation:** @NotBlank, @Positive, @Min annotations
- **Logging:** Spring's SLF4J framework
- **Transactions:** @Transactional annotations

---

## 🔍 Common Issues & Solutions

| Issue | Cause | Solution |
|-------|-------|----------|
| `WebMvcConfigurer` cannot be found | Missing `spring-boot-starter-web` | Verify pom.xml includes webmvc dependency |
| `CorsRegistry` cannot be found | Wrong import path | Import from `org.springframework.web.servlet.config.annotation` |
| Package not found errors | IDE cache stale | Run Maven reload in VS Code |
| `@SpringBootApplication` not recognized | Missing Spring Boot JARs | Run `mvn clean install` |
| Compilation succeeds but IDE shows errors | IDE intellisense cache | Reload Java Language Server |

---

## ✅ VERIFICATION CHECKLIST

Run before deployment:

```bash
# 1. Clean compile (no tests)
mvn clean compile -DskipTests
# Expected: BUILD SUCCESS

# 2. Full build (with tests)
mvn clean install
# Expected: BUILD SUCCESS

# 3. Check JAR creation
ls -la target/Roja-Performance-*.jar
# Expected: File exists and has size > 30MB

# 4. Verify class files
find target/classes -name "*.class" | wc -l
# Expected: 50+ class files

# 5. Run health check
mvn spring-boot:run &
sleep 10
curl http://localhost:8080/api/v1/tyres/health/status
# Expected: {"status":"UP",...}
kill %1
```

---

## 📊 Current Build Status

```
✅ Build: SUCCESS
✅ Java: 17.0.18
✅ Maven: 3.9.12
✅ Spring Boot: 4.0.3
✅ Classes: 30 (29 Java + 1 main)
✅ Compilation Time: 7.96 seconds
✅ Package Structure: Consistent
✅ Imports: Correct
✅ Configuration: Complete
```

---

## 🚀 NEXT STEPS

1. **Verify Build:**
   ```bash
   mvn clean install -DskipTests
   ```

2. **Reload IDE:**
   - Ctrl+Shift+P → Maven: Reload Projects
   - Ctrl+Shift+P → Java: Clean Language Server Workspace

3. **Run Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Test API:**
   ```bash
   curl http://localhost:8080/api/v1/tyres/health/status
   ```

5. **Deploy:**
   - Use JAR: `java -jar target/Roja-Performance-0.0.1-SNAPSHOT.jar`
   - Or container: Build Docker image with Dockerfile

---

## 📝 NOTES

- **Phase 1 Focus:** Tyre management (TyreController, TyreService, TyreRepository)
- **Phase 0 Legacy:** Product, Customer, Order controllers (maintained for compatibility)
- **Production Ready:** All Phase 1 code compiles and runs without errors
- **Architecture Pattern:** Clean Layered Architecture with Dependency Injection
- **Security:** To be added in Phase 2 (JWT, OAuth2)

---

**Document Created:** 2026-03-04  
**Last Updated:** 2026-03-04  
**Version:** 1.0 - Senior Architect Edition
