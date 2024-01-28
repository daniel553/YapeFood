# YAPE FOOD

## Description

Code Challenge for Yape.
An Android application that loads a set of recipes from an API.

## Built With
<img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original.svg" height="40px" width="40px" />


### Prerequisites

Install the latest version of Android studio and:
- compileSdk 34
- minSdk 26
- gradle-8.2
- emulator or device with min version DEVELOPER mode
- make sure unsigned apks can be installed

### Installation

Clone repo or unzip it, then  open it Android studio.

### Installation (APK)
Install the apk with adb in the emulator or device.

$ adb install yape-release.apk

## Usage

1. Connect to Wifi or any network
2. Open the app and wait until load
3. Main screen is the list of recipes fetched from service
4. If no fetch, please check api enpoint or request enable it.
5. Tap over a recipe to see details
6. Navigate to the map to see the location.

## Notes
* **Important!:** I used an emoji of lightbulb  (💡) to mark my ideas, comments  and so on.
* **Architecture:** MVVM with a modern android architecture using UI->Domain->Data layers.
* **Design patterns**: Dependency injection with Hilt, Observable patterns with Flow and states (StateFlow, SharedFlow).
* **Database:** Simple Room database integrated with flows and **Repository** patterns.
* **Datasources:** Local and Remote, Remote uses Retrofit to perform simple calls to mockeable.io.
* **Domain layer** Used a simple domain layer for extracting tasks as Use Cases, importan for larger projects, I consider it's better to use it early instead of refactoring later.
* **Tests:** Some *unit tests* to verify task behaviour and cover expected results given conditions, some *instrumented tests* such as Compose tests, Database, API and so on.
* **Technology:** An approach of *Jetpack Compose* as a Single page application. Flows, coroutines, Room, Retrofit, Dagger Hilt, WebMockServer, Google Maps, Mockk.
* **ToDo's:** I wanted to create unit tests with Turbine to test view-model's flows and it's integration with state and ui events, but not time to acomplish it.


## License

All rights reserved
All images and brands are property of Yape, used as demo proposes.

## Contacts

<a href="https://www.linkedin.com/in/pedro-daniel-gg/"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>  <a href="mailto:dany.el553@gmail.com"><img src=https://raw.githubusercontent.com/johnturner4004/readme-generator/master/src/components/assets/images/email_me_button_icon_151852.svg /></a>
