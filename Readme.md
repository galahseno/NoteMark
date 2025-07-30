<img src="assets/screenshots/cover.png" style="width: 100%; height: auto;"/>

**NoteMark** is an offline-first notes manager app built for the Mobile Dev Campus by [Phillip Lackner](https://pl-coding.com/campus) as part of the monthly challenge. This app is made for fun and to improve skills.

---

## Project Status

This project was divided in 4 different milestones that were launched every fortnight. Was built in June and July 2025.

### 🚨 Latest Features ###

- **General development**
  - Offline first architecture developed. App works completely offline and can be synced with the server with different intervals
  - Worker created to do the sync depending on the interval
- **Settings Screen**
  - Sync interval selection (15min, 30min, 1hour and manual) item
  - Sync Data item
- **Detail Note Screen**
  - Edit mode changed to save the note after 500-100s from the last time the user typed something

---

## 🧑🏽‍💻 Technical implementation

- ✅ Jetpack Compose.
- ✅ MVI architecture (multi-modularized).
- ✅ Compose Navigator.
- ✅ Koin dependency injection.
- ✅ Kor Client for Network calls.
- ✅ Material Design 3 components and theming.
- ✅ Data Store for user preferences (encrypted with security crypto).
- ✅ Room for database
- ✅ Offline first architecture
- ✅ Workers

---

## 🎥 Demo ##

https://github.com/user-attachments/assets/1ad28820-9854-4023-90b0-42538931fc12

## 📱 Screenshots (portrait = dark mode || landscape = light mode) ##

### All Notes ###

| Mobile Portrait                                                              | Mobile Landscape                                                               | 
|------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| <img src="assets/screenshots/home/home_mobile_portrait.png" width="600" /> | <img src="assets/screenshots/home/home_mobile_landscape.png" width="1200" /> | 

| Tablet Portrait                                                              | Tablet Landscape                                                               |
|------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| <img src="assets/screenshots/home/home_tablet_portrait.png" width="600" /> | <img src="assets/screenshots/home/home_tablet_landscape.png" width="1200" /> |

### Detail Notes ###

#### View Mode ####

| Portrait                                                                               | Landscape                                                                                | 
|----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| <img src="assets/screenshots/detail/view/detail_view_mode_portrait.png" width="600" /> | <img src="assets/screenshots/detail/view/detail_view_mode_landscape.png" width="1200" /> |

#### Read Mode ####

⚠️ Read mode is only available in landscape mode.

| Mobile Landscape (with UI controls)                                                      | Tablet Landscape (without UI controls)                                                     | 
|------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| <img src="assets/screenshots/detail/read/detail_read_mode_landscape.png" width="1200" /> | <img src="assets/screenshots/detail/read/detail_read_mode_landscape_2.png" width="1200" /> |

#### Edit Mode ####

| Portrait                                                                               | Landscape                                                                                | 
|----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| <img src="assets/screenshots/detail/edit/detail_edit_mode_portrait.png" width="600" /> | <img src="assets/screenshots/detail/edit/detail_edit_mode_landscape.png" width="1200" /> |

### Settings ###

| Mobile Portrait                                                                    | Mobile Landscape                                                               | 
|------------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| <img src="assets/screenshots/settings/settings_mobile_portrait.png" width="600" /> | <img src="assets/screenshots/settings/settings_mobile_landscape.png" width="1200" /> | 

| Tablet Portrait                                                                    | Tablet Landscape                                                                     |
|------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------|
| <img src="assets/screenshots/settings/settings_tablet_portrait.png" width="600" /> | <img src="assets/screenshots/settings/settings_tablet_landscape.png" width="1200" /> |

---

## 🪪 License

This project is an open-source and free to use. Feel free to fork and upload your commits.

## Acknowledge

- A deep learning about responsive applications. Designed for tablets and phones (landscape or portrait).
- Feeling more comfortable with Koin dependency injection.
- Ktor as network client 
- Data Store for user preferences.
- Workers
- Offline architecture

---
