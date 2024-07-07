# MyMolina HealthCare App

## Introduction

**MyMolina HealthCare** is a mobile application designed to provide Molina Healthcare members with easy access to their health information and services. This is only an app that mocks some basic functionalities of the original app. The app allows users to locate branches and physicians, chat with support, contact customer service, and access urgent care and helpline services. Authentication is managed through Firebase, ensuring secure sign-in and registration processes.

**Molina Healthcare Inc.**, based in Bothell, WA, offers a range of healthcare services to its members. For more details, you can visit the app on the [Google Play Store](https://play.google.com/store/apps/details?id=com.molina.mobile.myhealthinhand&hl=en&gl=US).

## Features

- **Branch Locator**: Easily find Molina Healthcare branches.
- **Physician Locator**: Search for physicians by name, specialty, and location.
- **Chat**: Simple chat functionality for quick responses.
- **Contact Us**: Get in touch with customer support via deep linking.
- **Urgent Care**: Access urgent care information and call 911 if needed.
- **Help Line**: Simple support functionality for help and inquiries.
- **Authentication**: Secure sign-in and registration using Firebase.
- **Unit Testing**: Includes samples of unit testing for various features.

## Tech Stack

### Core

- **Kotlin**: The programming language used for Android development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Material 3**: Design system for consistent and beautiful UI components.

### AndroidX

- **Core KTX**: Extensions for Kotlin for more concise code.
- **Lifecycle Runtime KTX**: Lifecycle-aware components.
- **Activity Compose**: Integration for Jetpack Compose within activities.
- **Navigation Compose**: Framework for in-app navigation.
- **Hilt Navigation Compose**: Dependency injection support for navigation.
- **Work Runtime KTX**: Manage background jobs.
- **Hilt**: Dependency injection library.

### Networking

- **Retrofit**: Type-safe HTTP client for Android and Java.
- **GSON**: JSON conversion library.
- **OkHttp**: HTTP client.
- **Logging Interceptor**: Logs HTTP request and response data.

### UI & Image Loading

- **Coil**: Image loading library for Android backed by Kotlin Coroutines.
- **Material Icons Extended**: Additional material icons support.

### Firebase

- **Firebase Auth**: Authentication services for Firebase.

### Testing

- **JUnit**: Testing framework.
- **Espresso**: UI testing framework.
- **Mockito**: Mocking framework for unit tests.
- **Kotlin Coroutines Test**: Testing support for Kotlin Coroutines.
- **LeakCanary**: Memory leak detection library.

### Miscellaneous

- **LeakCanary**: Memory leak detection for Android.
- **Kotlin Reflect**: Reflection library for Kotlin.

## Getting Started

To get started with the project, clone the repository and open it in Android Studio. Make sure to have the necessary SDKs and tools installed.

1. Clone the repository:
    ```sh
    git clone https://github.com/waleHa/MyMolina.git
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Project Structure

The project is structured into modules and follows a clean architecture pattern. Core functionalities are isolated, and the use of dependency injection ensures that the components are loosely coupled and easier to test.
