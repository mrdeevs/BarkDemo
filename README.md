# BarkDemo -> Disable the default Camera app on the device

## Class files: AdbViewModel.kt, DisableCameraActivity.kt

## Process :

- Investigated PackageManager here: https://developer.android.com/reference/android/content/pm/PackageManager#public-methods
- Unfortunately, the APIs only let you query if a given package name is enabled / disabled : https://developer.android.com/reference/android/content/pm/PackageManager#getComponentEnabledSetting(android.content.ComponentName)   
- Discovered an alternative way: Run ADB shell commands on the device using code

val process = Runtime.getRuntime().exec(command)
process.waitFor()
  
- I made this logic asynchronous and thread safe in a view model
- Then I had to refresh on various ADB commands since it has been a while
- I got a basic example working that would let me run "pm list packages"
- This also required me to find out the most optimal way to read an InputStream and convert it into results:

val results = process.inputStream.bufferedReader().use(BufferedReader::readText)

- Once this was working I decided to set my focus on actually disabling the camera package. 
- The ADB shell command to do this is: pm disable package_or_component
- This important distinction here in the API is "Disable the given package or component (written as "package/class")."
- So I had to find a way to access which activity was associated with the camera, in addition to its package name to be able to disable it
- To accomplish this I ran: "dumpsys activity activities | grep camera" from the command line to get an output list of names of the Activity stack for the given camera
- Now I can try to run the command: "pm disable com.google.android.GoogleCamera/com.android.camera.CameraLauncher"
- Unfortunately it fails due to a security exception though, indicating that we need root access 

## Learning: We need root access to be able to successfully disable another package using ADB shell commands from code. Additionally, some OEMs prevent the disabling of default installed apps
