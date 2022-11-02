package com.ambiws.testassignment.core.extensions

import kotlinx.coroutines.channels.SendChannel

/**
 * Workaround b/c of exceptions that [SendChannel.offer] throws in case of closed channel..
 * @return false if any exception catched on offer function
 */
fun <E> SendChannel<E>.offerCatching(element: E): Boolean {
    return runCatching { trySend(element).isSuccess }.getOrDefault(false)
}
