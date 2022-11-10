package com.escodro.alkaa.presentation.home

import com.escodro.alkaa.model.AlkaaTopDestination

internal data class HomeActions(
    val onTaskClick: (Long) -> Unit = {},
    val onAboutClick: () -> Unit = {},
    val onTrackerClick: () -> Unit = {},
    val onOpenSourceClick: () -> Unit = {},
    val onTaskSheetOpen: () -> Unit = {},
    val onCategorySheetOpen: (Long?) -> Unit = {},
    val setCurrentSection: (AlkaaTopDestination) -> Unit = {}
)
