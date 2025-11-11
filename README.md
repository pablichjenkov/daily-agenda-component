## Daily Agenda View
Daily Agenda View is a compose multiplatform component useful for Apps that need to present daily events or daily activities type of views.
<BR/>
<BR/>
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

|Agenda Version|Kotlin Version|
|--|--|
|0.7.0|2.2.21|

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

<BR/>

Lets create a helper class to build the initial events data.

```kotlin
class SampleDataBuilder(slotsGenerator: SlotsGenerator) {
  val slotToEventMap = mutableMapOf<Slot, List<Event>>() // A Map<Slot, List<Event>> is the data structure which DailyAgendaView will render.
  val slot8_00 = slotsGenerator.getSlotForTime(8.0F) // Get a slot for the specific time you want to add an event. In this case 8.00 AM

  // This adds two event in the 8:00 AM slot
  slotToEventMap[slot8_00] =  
    listOf(
        Event(startSlot = startSlot, title = "Ev 1", startTime = 8.0F, endTime = 12.0F),
        Event(startSlot = startSlot, title = "Ev 2", startTime = 8.0F, endTime = 10.0F)
    )
}
```

<BR/>

Then add a **DailyAgendaView** in your Composable function like in the code snippet bellow:

```kotlin
@Composable
fun Box(modifier = Modifier.fillMaxSize()) {

  val dailyAgendaStateController = remember {
    val slotsGenerator = TimeLineSlotsGenerator()
    val demoConfigurations = DemoConfigurations(slotsGenerator)
    val slotToEventMap = SampleDataBuilder(slotsGenerator = slotsGenerator).slotToEventMap // See Sample0 .. Sample3 classes in the composeApp module for more detail

    DailyAgendaStateController(
        slotsGenerator = slotsGenerator,
        slotToEventMap = slotToEventMap,
        config = demoConfigurations.demoConfigMixedDirections
    )
  }
    
  DailyAgendaView(dailyAgendaState = dailyAgendaStateController.state.value) { event ->
        Box(
            modifier =
                Modifier.fillMaxSize().padding(2.dp).background(color = Color.LightGray)
        ) {
            Text(text = "${event.title}: ${event.startTime}-${event.endTime}") 
        }
  }
    
}
```

Above code is all you need to have a daily events timeline added to your App. Bellow is a showcase of the different layout configurations the component offers. 

**1.** The default configuration if you don't specify any. In this mode the agenda view will try to maximize the events witdh. It achieves that by mixing the rows layout direction. **Even rows** are rendered from left to right while **odd rows** are rendered from right to left. Since the events are order by duration, this mode leverage the maximum space available by laying out in the opposite direction from the previous road. It should be very effective in most data use cases.

```kotlin
config = Config.MixedDirections(eventWidthType = EventWidthType.VariableSize)
```

<img width="300" alt="daily-agenda-mix-directions-variable-width" src="https://github.com/user-attachments/assets/a914051f-0da5-4bcf-9519-037bebae4cb5" />

---

**2.** Similar to above, this mode also mixes the direction of the layout, even rows do LTR and odd rows fo RTL. But in this mode all the events have the same with. This is for the case where maximum space wants to be coverred but at the same time esthetic is needed.

```kotlin
config = Config.MixedDirections(eventWidthType = EventWidthType.FixedSize)
```

<img width="300" alt="daily-agenda-mix-directions-same-width" src="https://github.com/user-attachments/assets/f8b432fa-03b1-4764-852b-31de5e852dd1" />

---

**3.** This mode is just like number 2 but expand the single slot events to occupy the full row available width.

```kotlin
config = Config.MixedDirections(eventWidthType = EventWidthType.FixedSizeFillLastEvent)
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
config = Config.LeftToRight(lastEventFillRow = true)
```

<img width="300" alt="daily-agenda-LTR-fill-end" src="https://github.com/user-attachments/assets/a67d206c-c92f-4eee-b0b6-64ce4fcaa823" />

---

**5.** Similar to number 4 but in this case we want all the events to have the same width.

```kotlin
config = Config.LeftToRight(lastEventFillRow = false)
```

<img width="300" alt="daily-agenda-LTR-no-fill-end" src="https://github.com/user-attachments/assets/a4595b2e-9b29-4bbd-8a4d-6b8c3688163d" />

---

**6.** The same as number 4 but from Right to left. Could be useful in countries where languages are written/read from right to left.

```kotlin
config = Config.RightToLeft(lastEventFillRow = true)
```

<img width="300" alt="daily-agenda-RTL-fill-end" src="https://github.com/user-attachments/assets/aef40594-2ba2-4c00-b93c-8f9eec8b30d2" />

---

**7.** The same as number 5 but from Right to left.

```kotlin
config = Config.RightToLeft(lastEventFillRow = false)
```

<img width="300" alt="daily-agenda-RTL-no-fill-end" src="https://github.com/user-attachments/assets/9994e98d-6d2d-4168-b2b4-59ac45e5210e" />


## Contributions

We welcome contributions from the community! If you have ideas for new features, bug fixes, or improvements, please open an issue or submit a pull request.
