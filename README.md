# NBA Players App

A modern Android application that displays NBA players using Jetpack Compose and follows MVVM
architecture. The app demonstrates best practices in Android development including pagination,
caching, and clean architecture.

## Features

- Display NBA players in a responsive grid layout
- Built entirely with Jetpack Compose and Material 3
- Endless scrolling with Paging 3 and pull-to-refresh
- Shared element transitions between screens
- Offline-first support with Room database caching
- Remote NBA data from the Balldontlie API
- ️Headshots and team logos with Glide Compose
- Thorough unit testing across layers
- Clean, modular MVVM architecture using Clean Architecture principles
- Note: All images in this app are AI-generated and free to use. No real NBA assets are included.

## Tech Stack

### UI

- Jetpack Compose
- Material 3 Design System
- Shared element transitions (Compose Animation)
- Compose Navigation
- Glide Compose

### Architecture

- MVVM
- Clean Architecture
- Repository pattern
- Use cases

### Data

- Retrofit
- Paging 3
- Room
- Kotlinx Serialization

### Dependency Injection

- Dagger Hilt

### Testing

- JUnit 5
- Mockk
- Turbine
- Robolectric
- Room Testing Utilities

### Navigation

- Type-safe navigation with Compose Navigation
- SharedTransitionLayout for animated transitions between screens

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
│   └── usecase/        # Use cases
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

The project includes unit tests. To run the tests:

```bash
./gradlew testReleaseUnitTest
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
