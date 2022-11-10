package com.escodro.alkaa.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.escodro.alkaa.R
import com.escodro.alkaa.navigation.CategoryDestination
import com.escodro.alkaa.navigation.PreferenceDestination
import com.escodro.alkaa.navigation.SearchDestination
import com.escodro.alkaa.navigation.TaskDestination

/**
 * Enum to represent the sections available in the bottom app bar.
 *
 * @property title title to be shown in top app bar.
 * @property icon icon to be shown in the bottom app bar
 */
enum class AlkaaTopDestination(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
    val startDestination: String,
) {
    Tasks(
        R.string.home_title_tasks,
        Icons.Outlined.Check,
        route = TaskDestination.route,
        startDestination = TaskDestination.startDestination
    ),
    Search(
        R.string.home_title_search,
        Icons.Outlined.Search,
        route = SearchDestination.route,
        startDestination = TaskDestination.startDestination
    ),
    Categories(
        R.string.home_title_categories,
        Icons.Outlined.Bookmark,
        route = CategoryDestination.route,
        startDestination = CategoryDestination.startDestination
    ),
    Settings(
        R.string.home_title_settings,
        Icons.Outlined.MoreHoriz,
        route = PreferenceDestination.route,
        startDestination = SearchDestination.startDestination
    ),
}
