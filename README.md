# Dynamic App Icon in Android Without Restarting the App

This project demonstrates how to dynamically change the **app icon in Android** using **Foreground Service** and **WorkManager** without requiring the app to close.

## 🚀 Features
- Change the app icon at runtime without restarting the app.
- Use **Foreground Service** to keep the app running while updating the icon.
- Handle app kill scenarios using **onTaskRemoved()** to persist icon changes.
- Switch between different app icons using **activity-alias** in `AndroidManifest.xml`.

## 📂 Source Code
Check out the complete source code here:
👉 **[GitHub Repository](https://github.com/jacky8381/DynamicAppIcon)**

## 🎥 Demo
Watch the video demo to see the feature in action:
👉 **[Video Demo](https://drive.google.com/file/d/1ugUFYnRj8wmN5nkMgjxnmmWrZkGh9acW/view?usp=drivesdk)**

## 🛠️ Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **Foreground Service**
- **WorkManager**
- **AndroidManifest.xml (activity-alias)**

## 📜 How It Works
1. The user selects an icon from the UI.
2. A **Foreground Service** is started, setting the selected icon.
3. If the app is killed, **WorkManager** ensures the change persists.
4. The new icon is applied when the app restarts.

## 📜 How to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/jacky8381/DynamicAppIcon.git
2. Open the project in Android Studio.
3. Sync the Gradle files and build the project.
4. Run the app on an emulator or a physical device.
