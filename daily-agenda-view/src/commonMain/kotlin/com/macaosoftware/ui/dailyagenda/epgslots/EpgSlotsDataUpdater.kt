package com.macaosoftware.ui.dailyagenda.epgslots

class EpgSlotsDataUpdater internal constructor(
    val epgSlotsStateController: EpgSlotsStateController
) {

    fun addChannel(epgChannel: EpgChannel): Boolean {
        return epgSlotsStateController.epgChannels.add(epgChannel)
    }

    internal fun commit() {
        epgSlotsStateController.updateState()
    }

    fun postUpdate(block: EpgSlotsDataUpdater.() -> Unit) {
        this.block()
        commit()
    }
}
