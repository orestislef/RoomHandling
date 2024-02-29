# Room Database with Thread Handlers and Callbacks Demo

This Android application demonstrates the usage of Room Persistence Library for local database management, along with Thread Handlers and Callbacks for asynchronous operations.

## Overview

The Room Database with Thread Handlers and Callbacks Demo showcases the implementation of a local SQLite database using the Room Persistence Library. It incorporates Thread Handlers and Callbacks to perform database operations asynchronously, ensuring smooth user experience and avoiding UI thread blocking.

## Features

- Setup of a local SQLite database using Room Persistence Library.
- Implementation of asynchronous database operations using Thread Handlers.
- Usage of Callbacks to handle completion events and update UI accordingly.

## How to Use

1. Clone or download this repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
4. Explore the sample functionalities provided by the app.
5. Observe the usage of Thread Handlers and Callbacks for database operations.

## Requirements

- Android Studio (version 21 or later)
- Android SDK (version 21 or later)

## Dependencies

This project relies on the following dependencies:

- Room Persistence Library: Provides an abstraction layer over SQLite database.
- AndroidX Libraries: Support libraries for Room and other components.

## Notes

- Room Persistence Library simplifies database management in Android applications by providing compile-time checks for SQL queries.
- Thread Handlers are used to perform database operations off the UI thread, ensuring smooth user experience.
- Callbacks are utilized to handle completion events and update the UI with the result of database operations.
- This demo app focuses on basic database operations. For complex scenarios, consider additional features like LiveData and ViewModel for better data management.

## License

This project is licensed under the [MIT License](LICENSE).

## Contributors

- [OrestisLef](https://github.com/orestislef) - Creator and maintainer

