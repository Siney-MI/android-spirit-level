# Android Digital Spirit Level

An interactive, hardware-focused Android utility application that leverages the device's internal motion sensors to simulate a digital spirit level.

## Key Features
- **Real-time Motion Tracking:** Continuously interfaces with `Sensor.TYPE_ACCELEROMETER` to fetch precise raw coordinate vectors across three spatial planes (X, Y, Z).
- **Trigonometric Tilting Analysis:** Translates raw gravitational force metrics into accurate numerical inclination angles for immediate surface levelling diagnostics.
- **Dynamic Bubble UI Simulation:** Implements a custom graphical UI representing a physical spirit level. The indicator bubble moves dynamically and fluidly across alignment thresholds in real time.
- **Signal Noise Filtering (Jitter Prevention):** Utilizes a custom data-buffering cache algorithm to filter out high-frequency sensory noise and hand-tremor fluctuations, ensuring stable and reliable UX alignment.

## Tech Stack
- **Language:** Kotlin
- **Sensors:** Android Hardware Accelerometer
- **UI & Graphics:** Dynamic UI Custom Canvas Rendering / State Binding

## Project Structure & Documentation
- `app/src/main/` - Sensor event processors, trigonometric math helpers, and the graphic layout containers.
- **`MA_UE4.pdf`** - **Full German Project Documentation.** This PDF is stored in the repository root and details sensor processing loops and interface mechanics.

---

# Deutsche Projektdokumentation
Die wissenschaftliche und technische Dokumentation dieser Anwendung befindet sich im Dokument **`MA_UE4.pdf`** direkt in diesem Repository. Sie beschreibt:
- Die Auslesung und mathematische Filterung der Beschleunigungssensordaten.
- Das Puffer-Konzept zur Glättung der Benutzeroberfläche gegen Sensorzittern.
