package com.escodro.alkaa.presentation.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.escodro.alkaa.model.AlkaaTopDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun rememberAlkaaAppState(
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberAnimatedNavController(bottomSheetNavigator)
): AlkaaAppState =
    remember(bottomSheetNavigator, navController) {
        AlkaaAppState(bottomSheetNavigator, navController)
    }

@OptIn(ExperimentalMaterialNavigationApi::class)
@Stable
class AlkaaAppState(
    val bottomSheetNavigator: BottomSheetNavigator,
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = isTopLevel(currentDestination?.route ?: "")

    val topLevelDestination: List<AlkaaTopDestination> = AlkaaTopDestination.values().toList()

    fun navigate(destination: String) {
        if (isTopLevel(destination)) {
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            navController.navigate(destination)
        }
    }

    private fun isTopLevel(destination: String): Boolean =
        AlkaaTopDestination.values().any { it.startDestination == destination }

    fun onUpPress() {
        navController.popBackStack()
    }
}
