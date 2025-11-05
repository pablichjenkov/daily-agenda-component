## Daily Agenda View
Daily Agenda View is a compose multiplatform component useful for Apps that need to present daily events or daily activities type of views. The idea came out after searching for a daily calendar similar to **Microsotf Outlook App for Android** but nothing came out of Github. There seems to be not many libraries offering this functionality and the ones that do, do not meet all the requirements.

## Supported Targets

| Platform | Supported |
|----------|:---------:|
| Android  |     ✅     |
| iOS      |     ✅     |
| Desktop  |     ✅     |
| JS       |     ✅     |
| Wasm     |     ✅     |

## Gradle Coordinates

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation("io.github.pablichjenkov:daily-agenda-view:0.1.0")
    }
}
```

## How to use it
Bellow there is some code snippets that illustrate the different configurations.

**1.** The default configuration if you don't specify any. In this mode the agenda view will try to maximize the events witdh. It achieves that by mixing the rows layout direction. **Even rows** are rendered from left to right while **odd rows** are rendered from right to left. Since the events are order by duration, this mode leverage the maximum space available by laying out in the opposite direction from the previous road. It should be very effective in most data use cases.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.MixedDirections(eventWidthType = EventWidthType.VariableSize) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-demo" src="https://github.com/user-attachments/assets/5de0ddd4-115d-4f06-8afb-9f7edab86fbe" />

---

**2.** Similar to above, this mode also mixes the direction of the layout, even rows do LTR and odd rows fo RTL. But in this mode all the events have the same with. This is for the case where maximum space wants to be coverred but at the same time esthetic is needed.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.MixedDirections(eventWidthType = EventWidthType.FixedSize) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-same-width" src="https://github.com/user-attachments/assets/20e380e7-2c81-45bc-bd7d-a2d6596f4b39" />

---

**3.** This mode is just like number 2 but expand the single slot events to occupy the full row available width.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.MixedDirections(eventWidthType = EventWidthType.FixedSizeFillLastEvent) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-mix-directions-fill-end" src="https://github.com/user-attachments/assets/fef4fa49-914a-4dd2-ab44-ef8b738a1132" />

---

**4.** Instead of maximizing space consumption, an App might want consistency laying out the daily calendar events. Bellow mode renders from left to right always and also expand the single slot events to occupy the full row available width.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.LeftToRight(lastEventFillRow = true) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-LTR-fill" src="https://github.com/user-attachments/assets/60d41a6f-6a8b-4321-9078-cc8ce1de0b10" />

---

**5.** Similar to number 4 but in this case we want all the events to have the same width.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.LeftToRight(lastEventFillRow = false) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-LTR-no-fill" src="https://github.com/user-attachments/assets/ccd8f8a7-ed2b-4ad1-a8e8-3ce589306119" />

---

**6.** The same as number 4 but from Right to left. Could be useful in countries where languages are written/read from right to left.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.RightToLeft(lastEventFillRow = true) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-RTL-fill" src="https://github.com/user-attachments/assets/817fa988-f43e-4bba-8a6d-b0e12f331769" />

---

**7.** The same as number 5 but from Right to left.

```kotlin
val dailyAgendaState = remember {
    DailyAgendaStateController(
            slots = Slots.slots,
            slotToEventMap = Sample3(Slots.slots).slotToEventMap,
            config = Config.RightToLeft(lastEventFillRow = false) // <-- Configuration
        )
        .computeNextState()
}

DailyAgendaView(dailyAgendaState = dailyAgendaState)
```

<img width="300" alt="daily-agenda-RTL-no-fill" src="https://github.com/user-attachments/assets/3a5c75c3-40d9-4243-b9b3-67830be66a66" />

## Contributions

We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open an issue or submit a pull request.
