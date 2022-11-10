package com.escodro.alkaa.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.core.net.toUri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.escodro.alkaa.navigation.AlkaaDestination.Companion.BaseUri
import com.escodro.task.presentation.add.AddTaskBottomSheet
import com.escodro.task.presentation.detail.main.TaskDetailSection
import com.escodro.task.presentation.list.TaskListSection
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

interface AlkaaDestination {
    val route: String
    val startDestination: String

    companion object {
        val BaseUri = "app://com.escodro.alkaa".toUri()
    }
}

object TaskDestination : AlkaaDestination {
    override val route = "task"
    override val startDestination = "task_start"

    const val detailArg = "task_id"
    const val detail = "task_detail/{$detailArg}"
    const val bottomSheet = "task_bottom_sheet"
    val detailDeepLink = "${BaseUri}/${detailArg}={${detailArg}}"

    fun createDetailRoute(taskId: Long) = "task_detail/$taskId"
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.taskGraph(onUpPress: () -> Unit, navigateTo: (String) -> Unit) {
    navigation(route = TaskDestination.route, startDestination = TaskDestination.startDestination) {
        composable(route = TaskDestination.startDestination) {
            TaskListSection(
                onItemClick = { taskId -> navigateTo(TaskDestination.createDetailRoute(taskId)) },
                onBottomShow = { navigateTo(TaskDestination.bottomSheet) }
            )
        }

        composable(
            route = TaskDestination.detail,
            arguments = listOf(navArgument(TaskDestination.detailArg) { type = NavType.LongType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = TaskDestination.detailDeepLink
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            TaskDetailSection(
                taskId = arguments.getLong(TaskDestination.detailArg),
                onUpPress = onUpPress
            )
        }

        bottomSheet(TaskDestination.bottomSheet) {
            AddTaskBottomSheet(onHideBottomSheet = onUpPress)
        }
    }
}
