package com.onionsquare.goabase

import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> singleEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1
    )
}
