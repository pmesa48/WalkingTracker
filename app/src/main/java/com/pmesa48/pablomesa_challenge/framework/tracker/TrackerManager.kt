package com.pmesa48.pablomesa_challenge.framework.tracker

import android.content.Intent
import com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus.ChangeServiceStatusUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.endactivity.EndActivityUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.filterlocations.FilterLocationByThresholdUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TrackerManager(
    private val changeServiceStatusUseCase: ChangeServiceStatusUseCase,
    private val endActivityUseCase: EndActivityUseCase,
    private val filterLocationByThresholdUseCase: FilterLocationByThresholdUseCase
) {

    private val serviceContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    fun onServiceEnd() {
        CoroutineScope(serviceContext).launch {
            changeServiceStatusUseCase.changeState(false)
            endActivityUseCase.end()
        }
    }

    fun onServiceStart() {
        CoroutineScope(serviceContext).launch {
            filterLocationByThresholdUseCase.get().collect { println(it) }
            changeServiceStatusUseCase.changeState(true)
        }
    }

    fun hasToStop(intent: Intent?) =
        intent?.action != null && intent.action.equals(TrackerService.STOP_ACTION)

    fun onDestroy() {
        serviceContext.cancel()
    }

}