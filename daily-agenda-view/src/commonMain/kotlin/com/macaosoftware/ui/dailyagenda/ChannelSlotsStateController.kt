package com.macaosoftware.ui.dailyagenda

import androidx.compose.runtime.mutableStateOf

class ChannelSlotsStateController {

    val state = mutableStateOf<ChannelSlotsState>(createInitialValue())

    fun createInitialValue(): ChannelSlotsState {
        return ChannelSlotsState(
            slots = emptyList(),
            channels = emptyList(),
            channelSlotsConfig = ChannelSlotsConfig()
        )
    }
}