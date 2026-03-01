# Hello World API — Spring Boot Assignment

A Spring Boot REST API that returns a greeting message based on the first letter of a provided name.

---

## How to Run the Application

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps

```bash
# Clone the repository
git clone https://github.com/primanthafdo/java-assignment.git
cd java-assignment

# Checkout the branch
git checkout develop        # Linux/Mac/Windows

# Build the project
./mvnw clean install        # Linux/Mac
mvnw.cmd clean install      # Windows

# Run the application
./mvnw spring-boot:run      # Linux/Mac
mvnw.cmd spring-boot:run    # Windows
```

The application will start on `http://localhost:8080`.

### API Endpoint

```
GET /api/assignment/hello-world?name={name}
```

**Success (200)** — name starts with A–M:
```json
{ "message": "Hello Alice" }
```

**Error (400)** — name starts with N–Z, is blank, null, starts with a digit or special character:
```json
{ "error": "Invalid Input" }
```

---

## How to Run the Tests

```bash
./mvnw test     # Linux/Mac
mvnw.cmd test   # Windows     
```

To run a specific test class:

```bash
# Controller (API) tests
./mvnw test -Dtest=GreetingControllerTest     # Linux/Mac
mvnw.cmd test -Dtest=GreetingControllerTest   # Windows

# Service (unit) tests
./mvnw test -Dtest=GreetingServiceTest        # Linux/Mac
mvnw.cmd test -Dtest=GreetingServiceTest      # Windows
```

### Test Coverage

| Test Class | Type | Description |
|---|---|---|
| `GreetingControllerTest` | Integration (MockMvc) | Tests HTTP layer — status codes, JSON response shape |
| `GreetingServiceTest` | Unit | Tests business logic directly — no Spring context |

---

## Assumptions

- **Alphabet midpoint is exclusive of 'N'** — names starting with A through M (inclusive) return a success response. Names starting with N through Z throw an `IllegalArgumentException`. The boundary character used is `'n'` (`firstLetter < 'n'`), so `M`/`m` is the last valid letter.

- **Only the first character matters** — the rest of the name is ignored. `"mike123"` returns a success response because `'m' < 'n'`.

- **Case-insensitive check** — the first letter is lowercased before comparison, so `"Alice"`, `"alice"`, and `"ALICE"` all behave the same.

- **Null and blank names are invalid** — a missing `name` query parameter (which Spring resolves as `null`), an empty string `""`, or a whitespace-only string `"   "` all return 400.

- **Non-letter first characters are invalid** — names starting with digits (`"1alice"`) or special characters (`"@lice"`) return 400.

- **Error handling is done via `IllegalArgumentException`** — the service throws this for all invalid inputs, and the controller catches it to return a 400 response with an `error` key. Success responses use a `message` key. These two keys are mutually exclusive in any given response.

- **`@CrossOrigin` is enabled globally** on the controller, meaning all origins are allowed. This is suitable for development but should be restricted in production.
