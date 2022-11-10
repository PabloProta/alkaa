package com.escodro.alkaa.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.escodro.alkaa.navigation.AlkaaDestination.Companion.BaseUri
import com.escodro.category.presentation.bottomsheet.CategoryBottomSheet
import com.escodro.category.presentation.list.CategoryListSection
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

object CategoryDestination : AlkaaDestination {
    override val route: String = "category"
    override val startDestination: String = "category_start"

    const val categoryIdArg = "category_id"
    const val bottomSheet = "task_bottom_sheet/{$categoryIdArg}"
    val detailDeepLink = "${BaseUri}/${categoryIdArg}={${categoryIdArg}}"

    fun createDetailRoute(categoryId: Long?): String {
        val id = categoryId ?: 0L
        return "task_bottom_sheet/$id"
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.categoryGraph(onUpPress: () -> Unit, navigateTo: (String) -> Unit) {
    navigation(
        route = CategoryDestination.route,
        startDestination = CategoryDestination.startDestination
    ) {
        composable(route = CategoryDestination.startDestination) {
            CategoryListSection(
                onShowBottomSheet = { id -> navigateTo(CategoryDestination.createDetailRoute(id)) }
            )
        }

        bottomSheet(
            route = CategoryDestination.bottomSheet,
            arguments = listOf(
                navArgument(CategoryDestination.categoryIdArg) {
                    type = NavType.LongType
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = CategoryDestination.detailDeepLink
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(CategoryDestination.categoryIdArg) ?: 0L
            CategoryBottomSheet(
                categoryId = id,
                onHideBottomSheet = onUpPress
            )
        }
    }
}
