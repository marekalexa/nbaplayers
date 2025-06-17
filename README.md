# NBA Players App

A modern Android application that displays NBA players using Jetpack Compose and follows MVVM architecture. The app demonstrates best practices in Android development including pagination, caching, and clean architecture.

## Features

- 🏀 Display NBA players in a grid layout
- 📱 Modern UI built with Jetpack Compose
- 🔄 Efficient pagination with Paging 3
- 💾 Local caching with Room database
- 🌐 Remote data fetching with Retrofit
- 🎨 Material 3 design system
- 🔍 Pull-to-refresh functionality
- 🖼️ Image loading with Glide
- 🧪 Comprehensive unit tests

## Tech Stack

- **UI Layer**
  - Jetpack Compose
  - Material 3
  - Compose Navigation
  - Glide for image loading

- **Architecture**
  - MVVM Architecture
  - Clean Architecture principles
  - Repository pattern
  - Use cases

- **Data Layer**
  - Room Database for local caching
  - Retrofit for network calls
  - Paging 3 for efficient data loading
  - Kotlinx Serialization

- **Dependency Injection**
  - Hilt

- **Testing**
  - JUnit
  - Mockk
  - Turbine for Flow testing
  - Robolectric
  - Room testing utilities

## Project Structure

```
app/
├── data/
│   ├── local/          # Room database and entities
│   ├── remote/         # Retrofit API and DTOs
│   ├── repository/     # Repository implementations
│   └── paging/         # Paging implementation
├── domain/
│   ├── model/          # Domain models
│   └── repository/     # Repository interfaces
└── ui/
    ├── screen/         # Compose screens
    ├── component/      # Reusable Compose components
    ├── model/          # UI models
    └── viewmodel/      # ViewModels
```

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## API Key

The app uses the [balldontlie API](https://www.balldontlie.io/) for NBA data. You'll need to:

1. Get an API key from balldontlie
2. Add it to your `local.properties` file:
```properties
BALLDONTLIE_API_KEY=your_api_key_here
```

## Testing

The project includes comprehensive unit tests. To run the tests:

```bash
./gradlew test
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
