# MOHSIN — Portfolio App

Ultra-luxury native Android portfolio for **Mohd Mohsin**.
Kotlin · XML Views · Material 3 · offline-first · zero internet permission.

## Build

```bash
cd ~/AndroidProjects/Mohsin
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

## Install (phone USB se)

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.mohsin.portfolio/.LaunchActivity
```

## Architecture

```
com.mohsin.portfolio/
├── PortfolioApp.kt          # theme restore on cold start
├── LaunchActivity.kt        # cinematic intro (skippable, reduced-motion aware)
├── MainActivity.kt          # fragment host + custom bottom nav (Home/Work/Journey/Connect)
├── data/                    # PortfolioData (all résumé content), ThemePrefs
├── models/                  # data classes
├── ui/
│   ├── home/                # signature editorial home (6 storytelling sections)
│   ├── about/               # editorial profile + principles
│   ├── work/                # project gallery + case-study detail
│   ├── journey/             # experience timeline, education, expertise, credentials, impact
│   ├── connect/             # contact actions, share, résumé entry
│   ├── resume/              # native PdfRenderer viewer + FileProvider share/open
│   └── settings/            # theme override bottom sheet (system/dark/light)
├── animations/              # fadeUp/fadeIn/countUp + reduced-motion guards
└── utils/                   # intents (email/dial/link/share/copy), edge-to-edge helpers
```

- **Stack:** AGP 8.13.2 · Gradle 8.14.6 · Kotlin 2.2.20 · minSdk 26 · targetSdk 36
- **Fonts:** Inter, Cormorant Garamond, JetBrains Mono (bundled OFL variable TTFs)
- **Content:** single source of truth in `data/PortfolioData.kt` — UI renders only
- **Resume:** bundled `assets/resume.pdf`, viewed via platform `PdfRenderer`, shared via FileProvider — no storage permissions
- **No INTERNET permission** — core experience fully offline

## Known limitations

- No physical-device QA yet (phone not connected during build) — verify scroll, insets, and theme switch on device
- Project screenshots not available — typography-based project presentations used (no fabricated imagery)
- Verification URLs for certifications not supplied — verify action hidden by design
- Debug APK is signed with the debug key (release signing not configured)

## Verification report (build-time)

- ✅ `assembleDebug` successful — 7.3 MB APK
- ✅ package `com.mohsin.portfolio`, label `MOHSIN`, minSdk 26
- ✅ assets/resume.pdf + bundled fonts + adaptive monogram icon present
- ✅ No INTERNET permission
