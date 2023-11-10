# Movie-Apps

## Screenshoots
<img src="screenshoots/app_main.jpeg" height="400" alt="Screenshot"/> <img src="screenshoots/app_detail.jpeg" height="400" alt="Screenshot"/> 
<img src="screenshoots/app_category.jpeg" height="400" alt="Screenshot"/> <img src="screenshoots/app_favorit_list.jpeg" height="400" alt="Screenshot"/> 

## Structure & Concept
- JDK 1.8
- MVVM + Data binding
- Multi-modules
- Navigation Component
- ViewModel
- LiveData
- Coroutines
- Koin
- Mockk

### Main Modules
#### App
You can basically leave this one out. App Module is now as small as possible and only used as entry point.
#### Data
This is your module for your data source. Connect to your API, manage cache, creating entities happened here.
#### Features
Each feature must be contained in a separate gradle module.
#### Navigation
This is used for routing purposes, especially between modules.
#### Common
Contains reusable classes and resources that you can use in another modules. We have already added some useful common features that you will need to start your project.

## Technical details
This app uses Kotlin as the main language.
The main libraries used in this repository are :

### App
* [Jetpack](https://developer.android.com/jetpack/) - Jetpack is a suite of libraries, tools, and guidance to help developers write high-quality apps easier.
* [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java
* [Koin](https://insert-koin.io/) - A pragmatic lightweight dependency injection framework for Kotlin developers. Written in pure Kotlin
* [OkHttp](https://github.com/square/okhttp/) - HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
* [Room](https://https://developer.android.com/training/data-storage/room) - Persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
