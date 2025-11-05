## Daily Agenda View
Daily Agenda View is a compose multiplatform component useful for Apps that need to present daily events or daily activities type of views. The idea came out after searching for a daily calendar similar to **Microsotf Outlook App for Android** but nothing came out of Github. There seems to be not many libraries offering this functionality and the ones that do, do not meet all the requirements.

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation("io.github.pablichjenkov:daily-agenda-view:0.1.0")
    }
}
```

| Platform | Supported |
|----------|:---------:|
| Android  |     ✅     |
| iOS      |     ✅     |
| Desktop  |     ✅     |
| JS       |     ✅     |
| Wasm     |     ✅     |


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

<img width="300" alt="daily-agenda-mix-directions-variable-width" src="https://github.com/user-attachments/assets/a914051f-0da5-4bcf-9519-037bebae4cb5" />

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

<img width="300" alt="daily-agenda-mix-directions-same-width" src="https://github.com/user-attachments/assets/f8b432fa-03b1-4764-852b-31de5e852dd1" />

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

<img width="300" alt="daily-agenda-mix-directions-same-width-fill-end" src="https://github.com/user-attachments/assets/f4db4ff1-700a-4af8-9ebe-a5969b2164b5" />

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

<img width="300" alt="daily-agenda-LTR-fill-end" src="https://github.com/user-attachments/assets/a67d206c-c92f-4eee-b0b6-64ce4fcaa823" />

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

<img width="300" alt="daily-agenda-LTR-no-fill-end" src="https://github.com/user-attachments/assets/a4595b2e-9b29-4bbd-8a4d-6b8c3688163d" />

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

<img width="300" alt="daily-agenda-RTL-fill-end" src="https://github.com/user-attachments/assets/aef40594-2ba2-4c00-b93c-8f9eec8b30d2" />

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

<img width="300" alt="daily-agenda-RTL-no-fill-end" src="https://github.com/user-attachments/assets/9994e98d-6d2d-4168-b2b4-59ac45e5210e" />


## Contributions

We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open an issue or submit a pull request.
