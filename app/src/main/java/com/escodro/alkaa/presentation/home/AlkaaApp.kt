package com.escodro.alkaa.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.escodro.alkaa.model.AlkaaTopDestination
import com.escodro.alkaa.navigation.AlkaaNavGraph
import com.escodro.designsystem.AlkaaTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun AlkaaApp(appState: AlkaaAppState = rememberAlkaaAppState()) {
    Scaffold(
        content = { paddingValues ->
            AlkaaNavGraph(
                navController = appState.navController,
                bottomSheetNavigator = appState.bottomSheetNavigator,
                onNavigate = appState::navigate,
                onUpPress = appState::onUpPress,
                modifier = Modifier.padding(paddingValues),
            )
        },
        bottomBar = {
            AnimatedVisibility(visible = appState.shouldShowBottomBar) {
                AlkaaBottomNav(
                    destinations = appState.topLevelDestination,
                    onNavigateToDestination = appState::navigate,
                    currentDestination = appState.currentDestination
                )
            }
        }
    )
}

@Composable
private fun AlkaaBottomNav(
    destinations: List<AlkaaTopDestination>,
    onNavigateToDestination: (String) -> Unit,
    currentDestination: NavDestination?
) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.background) {
        destinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            val colorState = animateColorAsState(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
            AlkaaBottomIcon(
                destination = destination,
                tint = colorState.value,
                onNavigateToDestination = onNavigateToDestination,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AlkaaBottomIcon(
    destination: AlkaaTopDestination,
    tint: Color,
    onNavigateToDestination: (String) -> Unit,
    modifier: Modifier
) {
    val title = stringResource(destination.title)
    IconButton(
        onClick = { onNavigateToDestination(destination.route) },
        modifier = modifier
    ) {
        Icon(imageVector = destination.icon, tint = tint, contentDescription = title)
    }
}

@Suppress("UndocumentedPublicFunction")
@Preview(showBackground = true)
@Composable
fun AlkaaBottomNavPreview() {
    AlkaaTheme {
        // TODO re-enable
        // AlkaaBottomNav(
        //     currentSection = AlkaaTopDestination.Tasks,
        //     onSectionSelect = {},
        //     items = AlkaaTopDestination.values().toList()
        // )
    }
}
