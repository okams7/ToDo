package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
            val navController = rememberNavController()
            val tasksViewModel: TasksViewModel = viewModel()
            val themeViewModel: ThemeViewModel = viewModel()

//            var isDarkTheme by remember { mutableStateOf(false) }

            ToDoAppTheme(themeViewModel.isDarkTheme.value) {
                Scaffold(
                    topBar = {
                        ToDoTopBar(themeViewModel,navController)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
//                    TaskListScreen(modifier = Modifier.padding(innerPadding))
                    NavigationHost(navController, modifier = Modifier.padding(innerPadding), tasksViewModel)
                }
            }
        }
    }
}