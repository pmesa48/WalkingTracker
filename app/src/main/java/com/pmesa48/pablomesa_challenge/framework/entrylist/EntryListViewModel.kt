package com.pmesa48.pablomesa_challenge.framework.entrylist

import androidx.lifecycle.ViewModel
import com.pmesa48.pablomesa_challenge.domain.usecase.changeservicestatus.ChangeServiceStatusUseCase
import com.pmesa48.pablomesa_challenge.domain.usecase.getentries.GetEntriesUseCase
import com.pmesa48.pablomesa_challenge.model.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject constructor(
    getEntriesUseCase: GetEntriesUseCase,
    changeServiceStatusUseCase: ChangeServiceStatusUseCase
) : ViewModel() {

    val serviceStatus =
        changeServiceStatusUseCase.getState()
            .map { if (it?.active == true) UiState.Updating else UiState.Stopped }

    val updates =
        getEntriesUseCase.get()
            .map { UiState.OnUpdate(it) }
            .onCompletion { UiState.Stopped }
            .onEmpty { UiState.Empty }

    sealed class UiState {
        object Stopped : UiState()
        object Empty : UiState()
        object Updating : UiState()
        data class OnUpdate(val entries: List<Entry>) : UiState()
    }
}