package com.tbi.moviecompose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * from chris banes code;
 * to safely collect flow only when view visible
 */
@Composable
fun <T> rememberFlowWithLifeCycle(
    flow: Flow<T>,
    lifeCycle:Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifeCycle) {
    flow.flowWithLifecycle(lifeCycle, minActiveState)
}