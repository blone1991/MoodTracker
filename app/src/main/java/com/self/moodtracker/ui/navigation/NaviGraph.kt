package com.self.moodtracker.ui.navigation

import android.graphics.drawable.Icon
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.self.moodtracker.BakingScreen
import com.self.moodtracker.ui.Setting.SettingScreen
import com.self.moodtracker.ui.home.HomeScreen
import com.self.moodtracker.ui.stats.StatsScreen

data class NavItem (val route: String, val title: String, val iconImage: ImageVector)
@Composable
fun NaviGraph (navController: NavHostController) {
    val navItems = listOf(
        NavItem("Home", "Mood Tracker", Icons.Default.Home),
        NavItem("Stats", "Mood Stats", Icons.Default.DateRange),
        NavItem("Setting", "Settings", Icons.Default.Settings),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"
    val currentNavItem = navItems.find { it.route == currentRoute } ?: navItems[0]

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Cyan),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = currentNavItem.title, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
            }
        },
        bottomBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
            ) {

                navItems.forEach {
                    Row (
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            navController.navigate(it.route) {
                                // 백스택 관리
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true // 상태 저장
                                }
                                launchSingleTop = true // 중복 화면 방지
                                restoreState = true // 상태 복원
                            }
                        }) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(imageVector = it.iconImage, contentDescription = it.route)
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->

        Column (modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)){

            NavHost(navController = navController, startDestination = "Home") {
                composable("Home") {
                    HomeScreen()
                }
                composable("Stats") {
                    StatsScreen()
                }
                composable("Setting") {
                    SettingScreen()
                }
            }
        }
    }
}