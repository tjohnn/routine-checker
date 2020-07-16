package com.tjohnn.routinechecks.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

}