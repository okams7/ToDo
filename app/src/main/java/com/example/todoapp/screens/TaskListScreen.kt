package com.example.todoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.components.TaskCard
import com.example.todoapp.data.Routes
import com.example.todoapp.viewmodels.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(modifier: Modifier, vm: TasksViewModel, navController: NavController) {

    // جمع حالة قائمة المهام من ViewModel
//    val tasks by vm.tasks.collectAsState()

    val tasks = vm.tasks
    val isLoading = vm.isLoading
    val error = vm.errorMessage

    val newTaskText by vm.newTaskText.collectAsState()

    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isLoading,
        onRefresh = { vm.fetchTasks() },
        state = refreshState,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            OutlinedTextField(
                value = newTaskText,
                onValueChange = { vm.updateNewTaskText(it) },
                label = { Text("Task") },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "All Tasks",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = { vm.addTask() },
                ) {
                    Text("Add Task")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    CircularProgressIndicator()
                }

                error != null -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            "Error!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        println("error: $error")
                        Text(error, style = MaterialTheme.typography.bodyMedium)
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.undraw_bug_fixing_sgk),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                            //                        .fillMaxHeight()
                        )
                    }

                }

                tasks.isEmpty() -> {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.empty_list),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxHeight()
                    )
                }

                else -> {
                    LazyColumn {
                        items(tasks) { task ->
                            TaskCard(
                                task,
                                onClick = {
                                    navController.navigate(Routes.TaskDetail.name + "/" + task.id)
                                }
                            )
                        }
                    }
                }
            }

            //        if (tasks.isEmpty()) {
            //            Image(
            //                imageVector = ImageVector.vectorResource(R.drawable.empty_list),
            //                contentDescription = null,
            //                modifier = Modifier
            //                    .align(Alignment.CenterHorizontally)
            //                    .fillMaxHeight()
            //            )
            //        } else {
            //
            //
            //        }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTaskListScreen() {
//    ToDoAppTheme {
//        TaskListScreen(modifier = Modifier)
//    }
//}