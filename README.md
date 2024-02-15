# Album App - Jetpack Compose

 Context for a "professional project" :
 You must create a native Android application displaying the list of the following items 
 (album titles): https://static.leboncoin.fr/img/shared/technical-test.json
 For each item, we want to see at least the title and the image. Please note, a constraint concerning
 images will force you to add a header with the "User-Agent" key for each request to retrieve an image.
 The data contained in this json must be downloaded by the app at Runtime, and not manually put 
 in the app's assets.

So for this project I make this choice (below) for have a high-performance, maintainable and
scalable application. (if the backend decide to give me a paginated json...)

# App : 
 * Display list of Album
 * Display detail of Album
 * Always listen network connectivity
 * Display a snackbar on no internet
 * Auto refresh when internet is back
 * Rotation keep position


* Clean Archi
  * Single activity architecture
  * MVVM for presentation layer
  * Modularized architecture
  * Domain layer for business logic
  * UI Layer is exposed with [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
  * [Android Architecture components](https://developer.android.com/topic/libraries/architecture)
  * Unit tests

# Module Data

This module contains the logic for accessing the application's data. It is responsible for 
retrieving and manipulating data from external sources such as APIs, databases, etc.

## Module Structure

- **Network** : This package contains the classes and interfaces necessary for making network calls
and retrieving data from remote sources with Retrofit 
(https://static.leboncoin.fr/img/shared/technical-test.json).

- **Local** : This package contains the classes and interfaces necessary for accessing data stored
locally on the device with Room (SQLite).

- **Repository** : This package contains concrete implementations of the repository interface 
defined in the Domain module. These implementations are responsible for coordinating between local 
and remote data sources to provide data to the application. For coordination, we will use the Paging
library, which allows for pagination and provides us with RemoteMediator to implement.

# Module Domain
This module contains the business logic of the application. It represents the functional core of 
the application and is independent of any platform-specific implementation, such as Android.

## Module Structure

- **Use Cases** : This package contains the application's use cases. Each use case represents a 
specific task of the application.

- **Repository** : This package contains the repository interfaces used to retrieve the data needed
for the use cases. This interface defines the methods for accessing data without specifying how this data is obtained (for example, from a local database or a remote service using RemoteMediator).

- **Model** : This package contains the application's business model.

#  UI Module
This module contains the entire UI layer in Compose, with the final screens of the app and the 
various states (error, loading, success).

## Package compose 
Is organized following the Atomic Design principle.
The idea behind Atomic Design is to organize components based on a clear hierarchy, to foster
reusability at every level.

Here are the different levels of Atomic Design:

- **tokens** are composed of some ground elements such as colors, shapes,
  typography, dimensions, icons
- **atoms** are the most basic components, that cannot be split into smaller components (e.g. text,
  buttons, switch etc...)
- **molecules** are more complex components, generally composed of multiple atoms (e.g. cells and
  cards)
- **organisms** are the most high level and complex components, generally representing entire
  sections of a page. It is composed of multiple molecules and atoms (e.g. masthead, sectionized lists
  etc...)


# Libraries
  * [Compose](https://developer.android.com/jetpack/compose)
  * [Material design](https://material.io/design)
  * [Dark/Lite theme](https://material.io/design/color/dark-theme.html)
  * [Kotlin](https://kotlinlang.org/)
  * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
  * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
  * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) for navigation between composables
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
  * [Retrofit](https://square.github.io/retrofit/) for networking
  * [Coil](https://github.com/coil-kt/coil) for image loading
  * [Mockito](https://site.mockito.org/) for unit test
  * [Turbine](https://github.com/cashapp/turbine) for coroutine unit test
  * [Room](https://developer.android.com/training/data-storage/room) database
