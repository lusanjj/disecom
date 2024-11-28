# Distributed E-Commerce System

A modern distributed e-commerce application built with cutting-edge technologies, designed to showcase technical expertise and scalability for real-world solutions.

## 🚀 Project Goal
This project demonstrates a distributed system architecture for an e-commerce platform, leveraging **microservices**, **containerization**, and **cloud-native technologies**. The intention is to impress interviewers by implementing robust, scalable, and industry-standard practices.

---

## 🔧 Technologies Used
- **Backend:**
  - [Spring Boot](https://spring.io/projects/spring-boot) - Microservices framework for scalable backend development.
  - [JWT](https://jwt.io/) - Secure user authentication and authorization.
  - [Spring Security](https://spring.io/projects/spring-security) - Securing APIs and user management.
  - [MySQL](https://www.mysql.com/) - Relational database for storing user and product data.

- **Frontend:**
  - [React.js](https://reactjs.org/) (planned) - For building an interactive, user-friendly interface.

- **Infrastructure:**
  - [Docker](https://www.docker.com/) - Containerization for microservices.
  - [Kubernetes](https://kubernetes.io/) (planned) - Orchestrating distributed services.
  - [AWS](https://aws.amazon.com/) (planned) - Cloud hosting and deployment.

- **Testing:**
  - [Postman](https://www.postman.com/) - Manual API testing.
  - [Selenium](https://www.selenium.dev/) (planned) - Automated end-to-end testing.

- **CI/CD:**
  - [Jenkins](https://www.jenkins.io/) (planned) - For continuous integration and deployment.

---

## 🌟 Features (In Progress)
### Authentication Service (Completed)
- User registration with secure password hashing.
- Login with JWT-based authentication.
- Role-based access control (planned).

### E-Commerce Features (Planned)
- Product catalog: Browsing and searching for items.
- Cart management: Add/remove products and checkout.
- Order tracking: Real-time order updates.

### Scalability and Security
- Microservices architecture for modularity and scalability.
- Secure APIs with JWT and Spring Security.
- Cloud-native infrastructure (Docker, Kubernetes, AWS).

---

## 📂 Project Structure
```
auth-service/
├── src/
│   ├── main/
│   │   ├── java/com/shane/authservice/
│   │   │   ├── config/          # Configuration classes (e.g., SecurityConfig)
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── repository/      # Repository interfaces for database access
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utility classes (e.g., JWT utility)
│   │   ├── resources/
│   │       ├── application.properties  # Local configuration
│   ├── test/                    # Unit and integration tests
└── pom.xml                      # Maven build configuration
```

---

## 🛠️ How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/auth-service.git
   ```
2. Navigate to the project directory:
   ```bash
   cd auth-service
   ```
3. Build and run the project:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API at `http://localhost:8080`.

---

## 📈 Future Enhancements
- Implement a full-stack frontend using React.
- Add a product catalog and cart services.
- Integrate CI/CD pipelines with Jenkins.
- Deploy the system to AWS using Kubernetes for scaling and high availability.

---

## 📚 Why This Project?
This project serves as a showcase of technical and problem-solving skills, focusing on distributed systems, modern development practices, and scalability. It reflects a strong foundation in software engineering principles and a commitment to building real-world solutions.

---

## 🤝 Acknowledgements
Special thanks to open-source tools and the Spring community for providing robust frameworks and libraries to build this project.

---
