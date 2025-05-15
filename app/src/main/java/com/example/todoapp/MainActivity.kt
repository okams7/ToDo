package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.components.ToDoTopBar
import com.example.todoapp.navigations.NavigationHost
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.viewmodels.TasksViewModel
import com.example.todoapp.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val tasksViewModel: TasksViewModel = viewModel()
//            var isDarkTheme by remember { mutableStateOf(false) }
            val isDarkTheme by themeViewModel.isDarkTheme
            val navController = rememberNavController()
            ToDoAppTheme(isDarkTheme) {
                Scaffold(
                    topBar = {
                        ToDoTopBar(
                            isDarkTheme = isDarkTheme,
                            themeViewModel = themeViewModel,
                            navController = navController
//                            onToggleTheme = {
//                                isDarkTheme = !isDarkTheme
//                                println("test")
//                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
//                    TaskListScreen(modifier = Modifier.padding(innerPadding))
                    NavigationHost(
                        navController,
                        modifier = Modifier.padding(innerPadding),
                        tasksViewModel
                    )
                }
            }
        }
    }
}