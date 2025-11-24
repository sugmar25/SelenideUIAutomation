# SelenideUIAutomation
UI automation made easy with Selenide

A robust, scalable automation framework combining
**Selenide** for UI testing, **REST Assured** for API validation, and **JDBC** for database verification
— all wrapped in a clean, modular architecture. Designed for maintainability, security, and professional reporting with **Allure**.

---

## 📂 Project Structure
src/
└── test/
├── resources/
│   ├── features/           # Gherkin feature files (e.g., Careers.feature)
│   └── config.properties   # Externalized configuration
├── java/
│   └── org.gallup.access/
│       ├── hook/           # Global hooks for setup/teardown (Hooks.java)
│       ├── pages/          # Page Object classes (CareersPage.java)
│       ├── payload/        # API payload builders (PayloadFactory.java)
│       ├── runner/         # Cucumber test runners (TestRunner.java)
│       ├── stepdefinitions/# Step definitions for UI/API/DB (JobsSteps.java)
│       └── utils/          # Shared utilities (DBUtils.java, ConfigReader.java)
screenshots/                     # Failure screenshots captured during test runs
reports/                         # Test execution reports (Allure HTML, JSON)
.github/                         # GitHub workflows and CI/CD configuration
README.md                        # Project documentation
pom.xml                          # Maven dependencies and build configuration
.gitignore                       # Git exclusions

## ⚙️ How to Run

1. **Clone the repo**
   ```bash
   git clone https://github.com/sugmar25/SelenideUIAutomation.git

