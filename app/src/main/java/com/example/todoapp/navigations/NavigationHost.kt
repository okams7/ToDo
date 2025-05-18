package com.example.todoapp.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.data.Routes
import com.example.todoapp.screens.TaskDetailsScreen
import com.example.todoapp.screens.TaskListScreen
import com.example.todoapp.viewmodels.TasksViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier, tasksViewModel: TasksViewModel){

    NavHost(navController = navController, startDestination = Routes.TaskList.name){
        composable(Routes.TaskList.name) {
            TaskListScreen(modifier, tasksViewModel, navController)
        }
        composable(Routes.TaskDetail.name) {
            TaskDetailsScreen()
        }
    }

}