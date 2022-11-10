package com.escodro.alkaa.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.escodro.preference.presentation.About
import com.escodro.preference.presentation.OpenSource
import com.escodro.preference.presentation.PreferenceSection
import com.escodro.splitinstall.LoadFeature
import com.google.accompanist.navigation.animation.composable

object PreferenceDestination : AlkaaDestination {
    override val route: String = "preference"
    override val startDestination: String = "preference_start"

    const val about = "about"
    const val tracker = "tracker"
    const val openSource = "open_source"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.preferenceGraph(
    context: Context,
    onUpPress: () -> Unit,
    navigateTo: (String) -> Unit
) {
    navigation(
        route = PreferenceDestination.route,
        startDestination = PreferenceDestination.startDestination
    ) {
        composable(route = PreferenceDestination.startDestination) {
            PreferenceSection(
                onAboutClick = { navigateTo(PreferenceDestination.about) },
                onTrackerClick = { navigateTo(PreferenceDestination.tracker) },
                onOpenSourceClick = { navigateTo(PreferenceDestination.openSource) }
            )
        }

        dialog(PreferenceDestination.tracker) {
            LoadFeature(
                context = context,
                featureName = FeatureTracker,
                onDismiss = onUpPress
            ) {
                // Workaround to be able to use Dynamic Feature with Compose
                // https://issuetracker.google.com/issues/183677219
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(TrackerDeepLink)
                    `package` = context.packageName
                }
                context.startActivity(intent)
            }
        }

        composable(route = PreferenceDestination.about) {
            About(onUpPress = onUpPress)
        }

        composable(route = PreferenceDestination.openSource) {
            OpenSource(onUpPress = onUpPress)
        }
    }
}

private const val FeatureTracker = "tracker"

private const val TrackerDeepLink = "app://com.escodro.tracker"
