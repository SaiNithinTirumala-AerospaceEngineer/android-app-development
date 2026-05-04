# Android App Development — UG Course Projects

![Java](https://img.shields.io/badge/Java-Android%20Studio-green)
![MATLAB](https://img.shields.io/badge/MATLAB-App%20Designer-orange)
![Android Studio](https://img.shields.io/badge/Android%20Studio-Panda%204-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)
![Institution](https://img.shields.io/badge/MRCET-Aeronautical%20Engineering-red)

## Overview

Five applications developed across two App Development courses during
B.Tech III Year in Aeronautical Engineering at Malla Reddy College of
Engineering and Technology (MRCET), Hyderabad, 2022–2023.

All applications were built, run on real devices and emulators, and
assessed as part of the curriculum under the guidance of Mrs. L. Sushma,
Associate Professor, Department of Aeronautical Engineering.

---

## Course structure

| Course | Semester | Year | App | Language |
|---|---|---|---|---|
| App Development 1 | III Year Sem 1 | 2022 | Quadratic Equation Solver | MATLAB App Designer |
| App Development 2 | III Year Sem 2 | 2022–23 | SMS Sending App | Java + Android Studio |
| App Development 2 | III Year Sem 2 | 2022–23 | Calculator | Java + Android Studio |
| App Development 2 | III Year Sem 2 | 2022–23 | Aerospace Quiz | Java + Android Studio |
| App Development 2 | III Year Sem 2 | 2022–23 | Temperature Converter | Java + Android Studio |

---

## App Development 1 — MATLAB

### Quadratic Equation Solver

Solves **ax² + bx + c = 0** for all real coefficients. Handles two
distinct real roots, one repeated root, and complex conjugate roots.
Built using MATLAB App Designer with a GUI interface.

**Key features:** Discriminant display · Complex root handling ·
Input validation (a=0 check) · Colour-coded status messages · Clear button

---

## App Development 2 — Android (Java)

### Temperature Converter

Real-time C/F/K converter — type in any field and the other two
update instantly via TextWatcher. All six directions supported with
absolute zero validation.

| UI | Celsius | Fahrenheit | Kelvin |
|---|---|---|---|
| ![UI](app-dev-2/temperature-converter/screenshots/screenshot_ui.png) | ![C](app-dev-2/temperature-converter/screenshots/screenshot_celsius.png) | ![F](app-dev-2/temperature-converter/screenshots/screenshot_fahrenheit.png) | ![K](app-dev-2/temperature-converter/screenshots/screenshot_kelvin.png) |

---

### Calculator

Full expression evaluator with operator precedence — handles
`100+100-20×5÷2 = 150`. Dark iOS-style UI with orange operators
and calculation history log.

| UI | Expression | Result | History |
|---|---|---|---|
| ![UI](app-dev-2/calculator-app/screenshots/screenshot_ui.png) | ![Expr](app-dev-2/calculator-app/screenshots/screenshot_expression.png) | ![Result](app-dev-2/calculator-app/screenshots/screenshot_result.png) | ![History](app-dev-2/calculator-app/screenshots/screenshot_history.png) |

---

### SMS Sending App — v1 and v2

Two versions maintained — original course submission (v1) and
enhanced Android Studio project (v2) with input validation.

**v1 — Original course submission (real device screenshots):**

| UI | Phone number | Message composed |
|---|---|---|
| ![UI](app-dev-2/sms-intent-app/screenshots/v1/sms_ui.png) | ![Num](app-dev-2/sms-intent-app/screenshots/v1/sms_number.png) | ![Msg](app-dev-2/sms-intent-app/screenshots/v1/sms_message.png) |

**v2 — Enhanced with validation (emulator screenshots):**

| UI | Validation | SMS launched |
|---|---|---|
| ![UI](app-dev-2/sms-intent-app/screenshots/v2/sms_ui.png) | ![Val](app-dev-2/sms-intent-app/screenshots/v2/sms_validation.png) | ![Sent](app-dev-2/sms-intent-app/screenshots/v2/sms_sent.png) |

---

### Aerospace Quiz

15-question multiple choice quiz covering aeronautical engineering,
physics and Android topics. 20-second countdown timer, colour-coded
feedback, and graded results screen.

| Welcome | Question | Answer | Results |
|---|---|---|---|
| ![Welcome](app-dev-2/quiz-app/screenshots/screenshot_welcome.png) | ![Q](app-dev-2/quiz-app/screenshots/screenshot_question.png) | ![A](app-dev-2/quiz-app/screenshots/screenshot_answer.png) | ![R](app-dev-2/quiz-app/screenshots/screenshot_results.png) |

---

## Repository structure

```
android-app-development/
├── app-dev-1/
│   └── quadratic-equation-solver/   ← MATLAB App Designer
│       ├── qadeqn.m
│       ├── screenshots/
│       └── README.md
│
├── app-dev-2/
│   ├── sms-intent-app/
│   │   ├── v1-original/             ← original course submission (3 files)
│   │   ├── v2-enhanced/             ← full Android Studio project
│   │   ├── screenshots/v1/          ← real device screenshots
│   │   ├── screenshots/v2/          ← emulator screenshots
│   │   └── README.md
│   │
│   ├── calculator-app/              ← full Android Studio project
│   │   ├── app/src/main/
│   │   ├── screenshots/
│   │   └── README.md
│   │
│   ├── quiz-app/                    ← full Android Studio project
│   │   ├── app/src/main/
│   │   ├── screenshots/
│   │   └── README.md
│   │
│   └── temperature-converter/       ← full Android Studio project
│       ├── app/src/main/
│       ├── screenshots/
│       └── README.md
│
├── docs/
│   ├── App_Development_1_Project_Report.pdf
│   └── App_Development_2_Project_Report.pdf
├── assets/
├── LICENSE
└── README.md
```

---

## How to open any app in Android Studio

1. Open Android Studio
2. File → Open → select the app folder (e.g. `calculator-app/`)
3. Wait for Gradle sync to complete
4. Connect device or start emulator → click Run ▶

**Min SDK:** API 23 · **Target SDK:** API 33 · **Language:** Java

---

## Developer

**Tirumala Sai Nithin** (20N31A2135)
B.Tech Aeronautical Engineering, MRCET Hyderabad
Guide: Mrs. L. Sushma, Associate Professor
Academic Year 2022–2023