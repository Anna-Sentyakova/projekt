package sample.kotlin.project.presentation.activities.main

import sample.kotlin.project.domain.stores.main.data.MainAction
import sample.kotlin.project.domain.stores.main.data.MainEvent
import sample.kotlin.project.domain.stores.main.data.MainState
import sample.kotlin.project.domain.stores.main.MainStore
import sample.kotlin.project.presentation.core.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    store: MainStore
) : BaseViewModel<MainState, MainAction, MainEvent>(store)
