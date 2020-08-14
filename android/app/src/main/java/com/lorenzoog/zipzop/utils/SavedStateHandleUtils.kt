package com.lorenzoog.zipzop.utils

import android.os.Bundle
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getMutableStateOf(
  key: String,
  default: T,
  save: (T) -> Bundle,
  restore: (Bundle) -> T
): MutableState<T> {
  val bundle = get<Bundle>(key)
  val initial =
    if (bundle == null) default
    else restore(bundle)

  return mutableStateOf(initial).also {
    setSavedStateProvider(key) {
      save(it.value)
    }
  }
}
