## Daily Agenda View
This is a compose view useful for Apps that needs to present daily events or daily activities type of functionality. The idea came out after searching for a daily calendar similar to Microsotf Outlook App for Android. There seems to be not many libraries offering this functionality and the ones that do do not meet all the requirements.

## Demo

```kotlin
Config(
    val layoutType = LayoutType.MixedDirections, // Even rows from left to right. Odd rows from right to left.
    val eventWidthType = EventWidthType.VariableSize
)
```

<img width="300" alt="daily-agenda-demo" src="https://github.com/user-attachments/assets/5de0ddd4-115d-4f06-8afb-9f7edab86fbe" />

---

```kotlin
Config(
    val layoutType = LayoutType.MixedDirections, // Even rows from left to right. Odd rows from right to left.
    val eventWidthType = EventWidthType.FixedSize
)
```

<img width="300" alt="daily-agenda-same-width" src="https://github.com/user-attachments/assets/20e380e7-2c81-45bc-bd7d-a2d6596f4b39" />

---

## Future work
At the moment the library uses only on method for laying out the daily events. It lays out **even rows from left to right** and **odd rows from right to left**, maximizing the space available in small screens. But some App may want a different type of layout, like always from left to right or viceverse.
