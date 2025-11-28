package com.macaosoftware.ui.dailyagenda.epgslots

class EpgSlotsDataUpdater internal constructor(
    val epgSlotsStateController: EpgSlotsStateController
) {

    fun addChannel(epgChannel: EpgChannel): Boolean {
        return epgSlotsStateController.epgChannels.add(epgChannel)
    }

    fun commit() {
        epgSlotsStateController.updateState()
    }
}