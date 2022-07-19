package com.tbi.moviecompose.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersProvider {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main

    val io: CoroutineDispatcher
        get() = Dispatchers.IO

    val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

class DefaultDispatcherProvider @Inject constructor(): DispatchersProvider