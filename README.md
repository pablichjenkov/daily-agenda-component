## Daily Agenda View
Daily Agenda View is a compose multiplatform component useful for Apps that need to present daily events or daily activities type of views.
<br/>
Use cases:
- Daily task planner apps.
- Appointment reservation or booking Apps.
- Tv Apps with show scheduling.
- Music Festival Apps with events overlapping at the same time.
- Great for TODO Apps to show all the TODOs in a day timeline view.
- Anything that comes to your mind.

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
| 1.1.0 | 2.2.21 | 1.9.3 |
| 1.0.2 | 2.2.21 | 1.9.3 |

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

The first thing you need is a **StateController**. You have to pick between **DecimalSlotsStateController** and
**TimeSlotsStateController**. **DecimalSlotsStateController** for decimal value axiz and **TimeSlotsStateController**
for hour and minute axis. **TvScheduleSlotController** (coming soon ...)
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
                addEventList(
                    startTime = LocalTime(hour = 8, minute = 0), // This is the slot start time
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

Now that you create a **SlotStateController** and add some events to it. Then add a **TimeSlotsView** 
in your Composable function:

```kotlin
@Composable
fun MySchedule(modifier = Modifier.fillMaxSize()) {

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


## Contributions

We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open an issue or submit a pull request.
