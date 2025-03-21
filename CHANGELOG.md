## 1.0.12

* Android: Complete rewrite of notification handling mechanism for Android 7 compatibility
* Android: Implemented separate code paths for different Android versions to avoid ClassNotFoundError
* Android: Eliminated all direct and indirect references to NotificationChannel in Android 7 code
* Android: Simplified notification creation with minimal fallback for guaranteed operation

## 1.0.11

* Android: Enhanced Android 7 (API 24-25) compatibility with reflection-based approach
* Android: Removed all direct references to NotificationChannel class to prevent ClassNotFoundError
* Android: Simplified notification fallback mechanism for bulletproof operation on older devices
* Android: Added detailed logging of notification creation process for better debugging

## 1.0.10

* Android: Added compatibility with Android 7 (API 24-25) devices
* Android: Fixed notification-related crashes on older Android versions
* Android: Implemented robust fallback mechanisms for notification creation
* Android: Improved error handling throughout notification pipeline
* Documentation: Updated README with Android 7 compatibility information

## 1.0.9

* Minor visibility fix: Make `NativeGeofenceException` visible to library users.

## 1.0.8

* Make plugin compatible with Flutter apps using Kotlin 2+.

## 1.0.7

* Fixes a bug with Android 30 and older. [#9](https://github.com/ChunkyTofuStudios/native_geofence/issues/9)
* Improve documentation.

## 1.0.6

* iOS: Improved background isolate spawning & cleanup routine.
* iOS: Fixes rare bug that may cause the goefence to triggering twice.

## 1.0.5

* Android: Specify Kotlin package when using Pigeon.

## 1.0.4

* Android: Use custom error class name to avoid naming conflicts ("Type FlutterError is defined multiple times") at build time.

## 1.0.3

* iOS: Removes `UIBackgroundModes.location` which was not required. Thanks @cbrauchli.

## 1.0.2

* iOS and Android: Process geofence callbacks sequentially; as opposed to in parallel.
* README changes.

## 1.0.1

* WASM support.
* Better documentation.
* Formatting fixes.

## 1.0.0

* Initial release.
