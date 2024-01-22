package com.yape.food

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * 💡Needed for DI with hilt
 */
@HiltAndroidApp
class MainApplication: Application()