## Daily Agenda View
This is a compose view useful for Apps that needs to present daily events or daily activities type of functionality. The idea came out after searching for a daily calendar similar to Microsotf Outlook App for Android. There seems to be not many libraries offering this functionality and the ones that do do not meet all the requirements.

## How to use it

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
