# Android POC

- a simple POC showing the effect of using main thread for costly operations

## How to use

1. Clone the project and open in Android Studio
2. Build the app
3. Press on `Main Thread` button, which will start image processing, which will freez the app, until the dask is done
4. Press `Revert` to restore the original image
5. Press `Coroutine` button, to perform the same image processing, this time in the non-blocking way
