# Pick-a-Spot API

Pick-a-Spot is a Spring Boot application that optimizes container placement in port yards based on distance, size, refrigeration needs, and availability.

## Features

- **Smart Placement Algorithm**: Uses Manhattan distance calculation and constraint-based scoring
- **High Performance**: O(n) complexity with sub-200ms response time for 20x20 yard grids
- **Simple REST Interface**: Easy-to-use POST endpoint for placement requests
- **Well-Tested**: Comprehensive test coverage including edge cases

## Technology Stack

- Java 17+
- Spring Boot 3
- Maven
- Jackson (JSON processing)
- JUnit 5 (testing)

## Prerequisites

- JDK 17 or newer
- Maven 3.6+

## Quick Start

1. **Build the application**
   ```
   mvn clean package
   ```

2. **Run the application**
   ```
   java -jar target/pick-a-spot-0.0.1-SNAPSHOT.jar
   ```

3. **Access the API**
   The service runs on port 8081 by default

## Project Structure

```
src/
├── main/
│   ├── java/dev/dt/pickspot/
│   │   ├── model/           # Domain objects
│   │   ├── dto/             # Data transfer objects
│   │   ├── service/         # Business logic
│   │   └── PickSpotApp.java # Main application and controller
│   └── resources/
│       └── application.properties
└── test/
    └── java/dev/dt/pickspot/
        └── PickerServiceTest.java
```

## API Documentation

### Endpoint: POST /pickSpot

Finds the optimal placement spot for a container.

#### Request Body

```json
{
  "container": {
    "id": "C1",
    "size": "small",        // "small" or "big"
    "needsCold": false,     // Whether refrigeration is required
    "x": 1, "y": 1         // Current container coordinates
  },
  "yardMap": [
    {
      "x": 1,
      "y": 2,
      "sizeCap": "small",   // "small" or "big" capacity
      "hasColdUnit": false, // Refrigeration capability
      "occupied": false     // Availability status
    }
  ]
}
```

#### Responses

**Success (200 OK)**
```json
{
  "containerId": "C1",
  "targetX": 1,
  "targetY": 2
}
```

**Error (400 Bad Request)**
```json
{
  "error": "no suitable slot"
}
```

## Algorithm Explanation

The container placement algorithm evaluates slots based on four key factors:

1. **Manhattan Distance**: `|containerX - slotX| + |containerY - slotY|`
   - Lower distances are preferred

2. **Size Compatibility**:
   - Compatible: No penalty (0)
   - Incompatible (big container, small slot): High penalty (10,000)

3. **Refrigeration Requirements**:
   - Met: No penalty (0)
   - Unmet: High penalty (10,000)

4. **Occupancy Status**:
   - Free: No penalty (0)
   - Occupied: High penalty (10,000)

The algorithm selects the slot with the lowest combined score.
If no suitable slot exists, the API returns a 400 error.





