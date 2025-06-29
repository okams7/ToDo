package com.example.todoapp.navigations

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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


const val duration = 500
const val offSet = 1500

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier,
    tasksViewModel: TasksViewModel
) {
    NavHost(navController = navController, startDestination = Routes.TaskList.name) {
        composable(
            Routes.TaskList.name,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it},
                    animationSpec = tween(
                        durationMillis = duration,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
//            popEnterTransition = {
//                slideInHorizontally(initialOffsetX = { -offSet })
//            },
//            popExitTransition = {
//                slideOutHorizontally(targetOffsetX = { offSet })
//            }
        )
        {
            TaskListScreen(modifier, tasksViewModel, navController)
        }
        composable(
            "${Routes.TaskDetail.name}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { offSet })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -offSet })
            },
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("taskId")
            TaskDetailsScreen(vm = tasksViewModel, taskId = itemId ?: -1)
        }
    }

}