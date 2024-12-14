
### **How to Run the Backend Locally**

#### **1. Prerequisites**
Ensure the following are installed:
- **Java Development Kit (JDK)**: Version 11 or later
- **Maven**: For dependency management and building the project
- **PostgreSQL Database with the EXTENSION Postgis**: Installed and running locally
- **Git**: To clone the repository

---

#### **2. Clone the Repository**
Run the following command in your terminal:
```bash
git clone https://github.com/Gestion-des-transactions-immobilieres/App_Backend.git
```

Navigate into the project directory:
```bash
cd <repository-folder>
```

---

#### **3. Configure the Database**

1. **Create the PostgreSQL Database**:
   - Open your PostgreSQL client or a tool like pgAdmin.
   - Create a new database:
     ```sql
     CREATE DATABASE Immobilier_DB;
     ```

2. **Set Up Database Schema**:
   Spring Boot will handle schema creation. You don’t need to manually create tables.

3. **Update `application.properties`**:
   Ensure the database connection details are correct in the `src/main/resources/application.properties` file:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/Immobilier_DB
   spring.datasource.username=postgres
   spring.datasource.password=<your password here>
   spring.jpa.hibernate.ddl-auto=create
   ```


---

#### **4. Install Dependencies**
Use Maven to install dependencies:
```bash
mvn clean install
```

---

#### **5. Run the Application**
Start the Spring Boot application:
```bash
mvn spring-boot:run
```

---

### **Common Issues and Fixes**

1. **Port Already in Use**:
   - If `8081` is already in use, change the port in `application.properties`:
     ```properties
     server.port=8082
     ```

2. **Database Connection Errors**:
   - Ensure PostgreSQL is running.
   - Double-check the database credentials in `application.properties`.

3. **Dependencies Not Downloading**:
   - Ensure Maven is installed and configured.
   - Run:
     ```bash
     mvn clean install
     ```

---

### **To use the Backend**

check the file endPointsDocumentation.xlsx to have an idea about the endpoints

