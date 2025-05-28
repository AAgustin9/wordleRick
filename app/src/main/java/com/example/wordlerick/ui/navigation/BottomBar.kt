package com.example.wordlerick.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlerick.ui.viewmodels.NavigationViewModel

@Composable
fun BottomBar(
    onNavigate: (String) -> Unit,
    viewModel: NavigationViewModel = viewModel()
) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()

    val homeTab = TabBarItem(title = WordleRickScreen.Home.name, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val rankingTab = TabBarItem(title = WordleRickScreen.Wiki.name, selectedIcon = Icons.Filled.Info, unselectedIcon = Icons.Outlined.Info)
    val profileTab = TabBarItem(title = WordleRickScreen.User.name, selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)
    val leaderboardTab = TabBarItem(title = WordleRickScreen.Leaderboard.name, selectedIcon = Icons.Filled.List, unselectedIcon = Icons.Outlined.List)

    val tabBarItems = listOf(homeTab, rankingTab, leaderboardTab, profileTab)

    TabView(tabBarItems, selectedTabIndex, onNavigate, viewModel)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

@Composable
fun TabView(
    tabBarItems: List<TabBarItem>,
    selectedTabIndex: Int,
    onNavigate: (String) -> Unit,
    viewModel: NavigationViewModel
) {
    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    viewModel.onTabSelected(index)
                    onNavigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = { Text(tabBarItem.title) })
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}