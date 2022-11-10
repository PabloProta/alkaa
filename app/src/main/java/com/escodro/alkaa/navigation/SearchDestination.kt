package com.escodro.alkaa.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.escodro.search.presentation.SearchSection
import com.google.accompanist.navigation.animation.composable

object SearchDestination : AlkaaDestination {
    override val route: String = "search"
    override val startDestination: String = "search_start"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchGraph(navigateToTask: (Long) -> Unit) {
    navigation(route = SearchDestination.route, startDestination = SearchDestination.startDestination) {
        composable(route = SearchDestination.startDestination) {
            SearchSection(onItemClick = navigateToTask)
        }
    }
}
