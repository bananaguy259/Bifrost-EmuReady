# Aurora — LED Controller for AYN Handhelds

Aurora is a custom LED controller for AYN handhelds (built and tested primarily on the **AYN Odin 2** family), providing a collection of background LED animations including:

- **Ambilight** — syncs your joystick LEDs to your screen's colors in real time
- **Audio Reactive** — LEDs pulse and move with your game's audio
- **Ambilight + Audio** — a hybrid of both
- **Classic animations** — Breathing, Rainbow, Pulse, Strobe, Sparkle, Fade, Party Mode, Chase, Static Color
- **Battery Indicator** — LEDs reflect your current battery level
- **Auto Brightness** — LED brightness automatically follows your screen's brightness

Aurora aims to bring a vibrant, customizable lighting experience to your handheld while keeping performance and battery consumption in mind.

> ⚠️ **Important:** For animations to work, **Aurora must stay alive in the background**. Closing the app or restricting notification (and screen recording for Ambilight) activity will stop LED updates.

---

# 🧬 Fork Notice

**Aurora is a fork of [Bifrost](https://github.com/Pollux-MoonBench/Bifrost)**, originally created by **Pollux-MoonBench** with major contributions from **KuriGohan-Kamehameha**. All core LED animation logic, the Odin/Thor hardware control layer, preset system, and app-profile switching originate from that project.

This fork focuses on:
- A redesigned UI and app identity (new color scheme, icons, onboarding flow, and branding)
- Simplified, renamed settings intended to be clearer for non-technical users
- A home screen widget for one-tap Start/Stop
- Auto Brightness (LED brightness synced to screen brightness)
- Removal of a few niche features (CPU temperature animation, built-in emoji icon presets) in favor of a more focused feature set

Per the terms of the GPLv3 license this project is distributed under, this notice documents that Aurora is a modified version of Bifrost. See [License](#-license) below.

---

# ✨ Features

## Ambilight

Uses Android's screen recording API to sample the screen's left and right average colors. For performance, Aurora captures the screen in **2×1 pixels** and reads the RGB values directly from the buffer.

### Options

- **Ignore black bars** to eliminate pillarboxing/letterboxing and favor more vivid colors (useful for older content)
- **Single color mode** to calculate one shared color for both sticks
- **Saturation boost slider** for more intense colors
- **Hex color input** for precise color selection

When enabled, the black-bar-aware sampler captures the screen using a **low-resolution 32-pixel grid**, allowing more advanced color analysis while remaining lightweight, favoring saturated colors and helping eliminate letterboxing.

---

## Audio Reactive

Analyzes live audio levels (using the screen recording permission) to drive LED intensity, using a single unified reactivity slider for responsive, smooth audio-driven animations.

---

## Ambilight + Audio

Combines Ambilight color sampling with Audio Reactive intensity for a hybrid effect. Supports the same black-bar ignoring, single color mode, and saturation boost options as Ambilight.

---

## Auto Brightness

When enabled, your LED brightness automatically scales to match your device's current screen brightness — dim your screen, and your joystick LEDs dim proportionally along with it.

---

## Animation Presets

- Save unlimited animation presets, each with their own full settings
- Rename or update any saved preset (including the default one) at any time
- Automatically loads the **last selected preset** on app launch
- Export/import presets as a versioned JSON bundle, with support for replacing or appending to your existing presets
- Assign a built-in icon or upload a custom image per preset

---

## App Profiles

- Assign specific presets to specific apps
- Aurora automatically switches presets depending on the **foreground application**
- Optional **fallback preset** for when no mapped app is in the foreground

---

## Performance / Update Speed

Aurora offers multiple update-speed profiles, from **Battery Saver** up to **Fastest**, which updates the LED controller as quickly as possible — this fastest mode may cause latency or instability on some devices.

---

## Independent LED Control

- Separate left/right LED control
- Each stick can run different colors or animations

## Charging Indicator

Improved LED feedback while charging: breathing lights while charging, charging speed indication, and a flash notification when charging completes.

---

## Home Screen Widget

A 1×2 home screen widget for instantly starting or stopping Aurora without opening the app, reflecting Aurora's live running state.

---

# 📦 Installation

This project builds via GitHub Actions — push to `main` and check the **Actions** tab for a downloadable debug APK artifact.

### Manual APK install (from a built artifact)

1. Download the built **APK**.
2. Open your **Downloads** folder.
3. Tap the APK file to start the installation.
4. If Android asks to allow installation from **unknown sources**, accept the permission.
5. Complete the installation.

---

# 🔒 Required Permissions

To enable Ambilight, Audio Reactive, and Ambilight + Audio modes, Aurora requires:

### Screen recording permission

Used exclusively to sample:

- Screen colors (Ambilight)
- Audio intensity (Audio Reactive)

Aurora does **not save or transmit screen contents** — sampling happens locally and is reduced to minimal pixel data for efficiency.

---

# ⚠️ Status

Aurora inherits Bifrost's underlying LED control and animation logic, which is stable but still evolving. Unexpected behavior may still occur on some devices, and this fork has been primarily tested on AYN Odin 2 hardware.

---

# ❤️ Credits

Aurora would not exist without the original work by **Pollux-MoonBench** and **KuriGohan-Kamehameha** on [Bifrost](https://github.com/Pollux-MoonBench/Bifrost). If you'd like to support the original project this fork is built on:

👉 https://ko-fi.com/pollux_moonbench

---

# 📜 License

This project is licensed under **GPLv3**, inherited from the original Bifrost project. You are free to use, study, modify, and redistribute this app under the terms of the GPLv3 license — see [LICENSE](LICENSE) for the full text.

This app is provided for free and **cannot be sold to you.**
