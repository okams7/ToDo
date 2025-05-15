package com.example.todoapp.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoapp.data.Routes
import com.example.todoapp.screens.TaskDetailsScreen
import com.example.todoapp.screens.TaskListScreen
import com.example.todoapp.viewmodels.TasksViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier,
    tasksViewModel: TasksViewModel
) {

    NavHost(navController = navController, startDestination = Routes.TaskLList.name) {
        composable(Routes.TaskLList.name) {
            TaskListScreen(
                modifier = modifier,
                navController = navController,
                vm = tasksViewModel
            )
        }
//        "details/{itemId}"
        composable(
            "${Routes.TaskDetails.name}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("taskId")
            TaskDetailsScreen(vm = tasksViewModel, taskId = itemId?: -1)
        }
    }

}