package com.escodro.alkaa.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout

/**
 * Navigation Graph to control the Alkaa navigation.
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Suppress("LongMethod", "MagicNumber")
@Composable
fun AlkaaNavGraph(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    onNavigate: (String) -> Unit,
    onUpPress: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = TaskDestination.route
) {
    val context = LocalContext.current
    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator, modifier = modifier) {
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            taskGraph(onUpPress = { onUpPress() }, navigateTo = onNavigate)

            searchGraph(
                navigateToTask = { taskId -> onNavigate(TaskDestination.createDetailRoute(taskId)) }
            )

            categoryGraph(onUpPress = { onUpPress() }, navigateTo = onNavigate)

            preferenceGraph(context = context, onUpPress = { onUpPress() }, navigateTo = onNavigate)
        }
    }
}
