## Daily Agenda View
Daily Agenda View is a clone of the calendar component seeing in the **Microsoft Outlook App** or **Microsoft Teams App**. The component layouts daily events across an hourly based timeline. It is great for task planner apps, appointment apps, event schedule apps, calendar apps.

Live demo using kotlin-wasm: [Daily Agenda View](https://pablichjenkov.github.io/daily-agenda-view)

## Support and Compatibility

<table>
<tr>
      <th>Platform Support</th>
      <th>Kotlin Compatibility</th>
</tr>
<tr>
  <td>

| Platform | Supported |
|----------|:---------:|
| Android  |     ✅     |
| iOS      |     ✅     |
| Desktop  |     ✅     |
| JS       |     ✅     |
| Wasm     |     ✅     |

  </td>
  <td>

| Agenda Version | Kotlin Version | CMP Version |
|---------------|--------|-------|
| 1.3.0 | 2.2.21 | 1.9.3 |
| 1.2.0 | 2.2.21 | 1.9.3 |

  </td>
</tr>
</table>

## How to use it

Add the gradle coordinates:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation("io.github.pablichjenkov:daily-agenda-view:<latest-version>")
    }
}
```

In Android only projects which already use **java-time** or **joda-time**, it is necessary to include the **kotlinx-datetime** dependecy too. So gradle will look like bellow.

```kotlin
dependencies {
    implementation("io.github.pablichjenkov:daily-agenda-view:<latest-version>")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
}
```

<br/>

The library API is based on the compose **StateController pattern**. A StateController is basically **a mini MVI store just for a specific UI component**.
Instead of being coupled to a full screen single gigantic state. The state controller is only bound to a specific composable ui section of the screen.
These are the options from the library:
- [TimeSlotsStateController](#TimeSlotsStateController)
- [DecimalSlotsStateController](#DecimalSlotsStateController)
- [EpgSlotsStateController](#EpgSlotsStateController)

### TimeSlotsStateController
The **TimeSlotsStateController** displays events in a hour and minute based timeline.

```kotlin
val timeSlotsStateController = remember {
    TimeSlotsStateController(
            timeSlotConfig = TimeSlotConfig(slotScale = 2, slotHeight = 48),
            eventsArrangement =
                EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
        ).apply {
            timeSlotsDataUpdater.postUpdate {
                addEvent(
                    uuid = Uuid.random(),
                    startTime = LocalTime(hour = 8, minute = 0),
                    endTime = LocalTime(hour = 8, minute = 30),
                    title = "Event 0",
                    description = "Description 0"
                )
                addEventList( // When adding a list all events must belong to the same slot.
                    startTime = LocalTime(hour = 8, minute = 0),
                    events =
                        listOf(
                            LocalTimeEvent(
                                uuid = Uuid.random(),
                                startTime = LocalTime(hour = 8, minute = 0),
                                endTime = LocalTime(hour = 8, minute = 45),
                                title = "Event 1",
                                description = "Description 1"
                            ),
                            LocalTimeEvent(
                                uuid = Uuid.random(),
                                startTime = LocalTime(hour = 8, minute = 0),
                                endTime = LocalTime(hour = 9, minute = 0),
                                title = "Event 2",
                                description = "Description 2"
                            )
                        )
                )
            }
        }
}

```

<br/>

Now that you create a **TimeSlotsStateController** and added some events to it. Then add a **TimeSlotsView** 
in your Composable screen.

```kotlin
@Composable
fun MyDayScheduleView(modifier = Modifier.fillMaxSize()) {

    val timeSlotsStateController = remember { ... }

    TimeSlotsView(timeSlotsStateController = timeSlotsStateController) { localTimeEvent ->
        Box(modifier = Modifier.fillMaxSize().padding(all = 2.dp).background(color = Color.Gray)) {
            Text(
                text =
                    "${localTimeEvent.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
                fontSize = 12.sp
            )
        }
    }
}
```

<img width="500" alt="desktop-demo" src="https://github.com/user-attachments/assets/440559b3-73f5-4a27-8594-8cb1350356c4" />

## Events Arrangement Options

**1.** In this mode the agenda view will try to maximize the events witdh. It achieves that by mixing the rows layout direction. **Even rows** are rendered from left to right while **odd rows** are rendered from right to left. Since the events are order by duration, this mode leverage the maximum space available by laying out in the opposite direction from the previous road. It should be very effective in most data use cases.

```kotlin
eventsArrangement = EventsArrangement.MixedDirections(eventWidthType = EventWidthType.VariableSize)
```

<img width="300" alt="daily-agenda-mix-directions-variable-width" src="https://github.com/user-attachments/assets/a914051f-0da5-4bcf-9519-037bebae4cb5" />

---

**2.** Similar to above, this mode also mixes the direction of the layout, even rows do LTR and odd rows fo RTL. But in this mode all the events have the same with. This is for the case where maximum space wants to be coverred but at the same time esthetic is needed.

```kotlin
eventsArrangement = EventsArrangement.MixedDirections(eventWidthType = EventWidthType.FixedSize)
```

<img width="300" alt="daily-agenda-mix-directions-same-width" src="https://github.com/user-attachments/assets/f8b432fa-03b1-4764-852b-31de5e852dd1" />

---

**3.** This mode is just like number 2 but expand the single slot events to occupy the full row available width. This is the default configuration if you don't specify any.

```kotlin
eventsArrangement = EventsArrangement.MixedDirections(eventWidthType = EventWidthType.FixedSizeFillLastEvent)
```

<table>
    <tr>
        <td>
            Events align with slot start/end time
        </td>
        <td>
            Events do not align with slot start/end time
        </td>
    </tr>
    <tr>
        <td>
            <img width="250" alt="daily-agenda-mix-directions-same-width-fill-end" src="https://github.com/user-attachments/assets/f4db4ff1-700a-4af8-9ebe-a5969b2164b5" />
        </td>
        <td><img width="250" alt="daily-agenda-mix-directions-same-width-fill-end" src="https://github.com/user-attachments/assets/81af8d8e-1d6d-404c-b81d-be6d3faec691" />
        </td>
    </tr>
</table>

---

**4.** Instead of maximizing space consumption, an App might want consistency laying out the daily calendar events. Bellow mode renders from left to right always and also expand the single slot events to occupy the full row available width.

```kotlin
eventsArrangement = EventsArrangement.LeftToRight(lastEventFillRow = true)
```

<img width="300" alt="daily-agenda-LTR-fill-end" src="https://github.com/user-attachments/assets/a67d206c-c92f-4eee-b0b6-64ce4fcaa823" />

---

**5.** Similar to number 4 but in this case we want all the events to have the same width.

```kotlin
eventsArrangement = EventsArrangement.LeftToRight(lastEventFillRow = false)
```

<img width="300" alt="daily-agenda-LTR-no-fill-end" src="https://github.com/user-attachments/assets/a4595b2e-9b29-4bbd-8a4d-6b8c3688163d" />

---

**6.** The same as number 4 but from Right to left. Could be useful in countries where languages are written/read from right to left.

```kotlin
eventsArrangement = EventsArrangement.RightToLeft(lastEventFillRow = true)
```

<img width="300" alt="daily-agenda-RTL-fill-end" src="https://github.com/user-attachments/assets/aef40594-2ba2-4c00-b93c-8f9eec8b30d2" />

---

**7.** The same as number 5 but from Right to left.

```kotlin
eventsArrangement = EventsArrangement.RightToLeft(lastEventFillRow = false)
```

<img width="300" alt="daily-agenda-RTL-no-fill-end" src="https://github.com/user-attachments/assets/9994e98d-6d2d-4168-b2b4-59ac45e5210e" />

### DecimalSlotsStateController
The **DecimalSlotsStateController** displays events in a vertical **decimal axis**. Although use cases for this type of data presentation are rare, the 
library includes it anyway in case someone needs it. This is actually the base StateController in which the TimeSlotsStateController builds upon.

```kotlin
DecimalSlotsStateController(
    decimalSlotConfig =
        DecimalSlotConfig(
            initialSlotValue = 7.0F,
            lastSlotValue = 19.0F,
            slotScale = 2,
            slotHeight = 48
        ),
    eventsArrangement = EventsArrangement.MixedDirections(EventWidthType.FixedSizeFillLastEvent)
)
    .apply {
        decimalSlotsDataUpdater.postUpdate {
            addDecimalEvent(
                DecimalEvent(
                    uuid = Uuid.random(),
                    title = "Ev0",
                    description = Constants.EmptyDescription,
                    startValue = 8.5F,
                    endValue = 10.0F
                )
            )
            addDecimalEventList(
                startValue = 8.0F,
                segments = listOf(
                    DecimalEvent(
                        uuid = Uuid.random(),
                        title = "Ev1",
                        description = Constants.EmptyDescription,
                        startValue = 8.0F,
                        endValue = 10.0F
                    ),
                    DecimalEvent(
                        uuid = Uuid.random(),
                        title = "Ev2",
                        description = Constants.EmptyDescription,
                        startValue = 8.0F,
                        endValue = 9.5F
                    )
                )
            )
        }
    }

```
Then use the **DecimalSlotsView** Composable like bellow:

```kotlin
// Assuming the StateController is hosted in a ViewModel
val decimalSlotsStateController =  viewModel.decimalSlotsStateController

DecimalSlotsView(decimalSlotsStateController = decimalSlotsStateController) { decimalEvent ->
    Text(text = "${decimalEvent.title}: ${decimalEvent.startValue}-${decimalEvent.endValue}", fontSize = 12.sp)
}
```
Above code should produce something like the image bellow. Notice the axis values are just decimal numbers, also this component doesn't have a current time line indicator.

<img width="476" height="617" alt="decimal-axis" src="https://github.com/user-attachments/assets/cd123807-520c-4935-9ee7-cbd7f27ed329" />

### EpgSlotsStateController
The library also includes an **EpgSlotsStateController**, EPG(Electronic Guide Program) is a very popular component in TV apps.
Although other uses cases can leverage this type of component too.

```kotlin
val epgSlotsStateController = remember {
    EpgSlotsStateController(
        EpgChannelSlotConfig(
            timeSlotConfig =
                TimeSlotConfig(startSlotTime = LocalTime(6, 0), endSlotTime = LocalTime(23, 59))
        )
    )
        .apply {
            epgSlotsDataUpdater.postUpdate {
                addChannel(
                    EpgChannel(
                        name = "Ch1",
                        events =
                            listOf(
                                LocalTimeEvent(
                                    uuid = Uuid.random(),
                                    title = "Ev1",
                                    description = Constants.EmptyDescription,
                                    startTime = LocalTime(9, 0),
                                    endTime = LocalTime(10, 0)
                                ),
                                LocalTimeEvent(
                                    uuid = Uuid.random(),
                                    title = "Ev2",
                                    description = Constants.EmptyDescription,
                                    startTime = LocalTime(10, 0),
                                    endTime = LocalTime(11, 30)
                                )
                            )
                    )
                )
                addChannel(
                    EpgChannel(
                        name = "Ch2",
                        events =
                            listOf(
                                LocalTimeEvent(
                                    uuid = Uuid.random(),
                                    title = "Ev3",
                                    description = Constants.EmptyDescription,
                                    startTime = LocalTime(9, 30),
                                    endTime = LocalTime(10, 15)
                                ),
                                LocalTimeEvent(
                                    uuid = Uuid.random(),
                                    title = "Ev4",
                                    description = Constants.EmptyDescription,
                                    startTime = LocalTime(10, 30),
                                    endTime = LocalTime(11, 0)
                                )
                            )
                    )
                )
            }
        }
}

```
Then use the **EpgSlotsView** to render the EpgSlotsStateController instance.

```kotlin
@Composable
fun MyTvScheduleView(modifier = Modifier.fillMaxSize()) {

    val epgSlotsStateController = remember { ... }

    EpgSlotsView(epgSlotsStateController = epgSlotsStateController) { localTimeEvent ->
        Text(
            text = "${localTimeEvent.title}: ${localTimeEvent.startTime}-${localTimeEvent.endTime}",
            fontSize = 12.sp
        )
    }
}
```

<img width="400" alt="epg-view" src="https://github.com/user-attachments/assets/dc5d6df5-f3ae-4477-bf8c-844ddfb4762c" />

## Contributions

We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open an issue or submit a pull request.
