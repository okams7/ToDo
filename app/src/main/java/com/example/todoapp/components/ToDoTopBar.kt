package com.example.todoapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.R
import com.example.todoapp.data.Routes
import com.example.todoapp.viewmodels.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoTopBar(
    isDarkTheme: Boolean,
    themeViewModel: ThemeViewModel,
    navController: NavController,
//              onToggleTheme: () -> Unit
) {
    // This will make the composable recompose when back stack changes
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    TopAppBar(
        title = { Text("ToDo App") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        actions = {
            Switch(
                modifier = Modifier.semantics {
                    contentDescription = "Demo with icon"
                },
                checked = isDarkTheme,
                onCheckedChange = {
//                    onToggleTheme()
                    themeViewModel.toggleTheme()
                },
                thumbContent = {
                    if (isDarkTheme) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.dark_mode),
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.light_mode),
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
        },
        navigationIcon = {
            if (currentRoute != Routes.TaskLList.name) {
                println("previousBackStackEntry")
                IconButton(
                    onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                println("no previousBackStackEntry")
                null
            }
        }
    )
}