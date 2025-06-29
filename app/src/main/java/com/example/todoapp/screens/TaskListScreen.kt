package com.example.todoapp.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.components.TaskCard
import com.example.todoapp.data.Routes
import com.example.todoapp.data.Task
import com.example.todoapp.viewmodels.TasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(modifier: Modifier, vm: TasksViewModel, navController: NavController) {

    // جمع حالة قائمة المهام من ViewModel
//    val tasks by vm.tasks.collectAsState()

    val tasks = vm.tasks
    val isLoading = vm.isLoading
    val error = vm.errorMessage
    val context = LocalContext.current

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
                    onClick = {
                        val task = vm.initTask()
                        if (task != null) {
                            vm.addTask(
                                task,
                                onSuccess = { vm.fetchTasks() },
                                onError = { error ->
                                    Toast.makeText(
                                        context,
                                        error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    },
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
                    // State for controlling the confirmation dialog
                    var showConfirmationDialog by remember { mutableStateOf(false) }
                    var itemToConfirmDelete by remember { mutableStateOf<Task?>(null) }
                    Box(modifier = Modifier.fillMaxSize()) { // Use a Box to allow AlertDialog to overlay
                        LazyColumn {
                            items(tasks, key = { task -> task.id!! }) { task ->
                                val dismissState = rememberSwipeToDismissBoxState(
                                    confirmValueChange = { dismissValue ->
                                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) { // Swiped from right to left
                                            itemToConfirmDelete = task // Set the item to be confirmed for deletion
                                            showConfirmationDialog = true
                                            //true // Confirm the dismiss
                                            false
                                        } else {
                                            false // Don't dismiss for other directions
                                        }
                                    },
                                    // Optional: Adjust positional threshold
                                    // positionalThreshold = { it * 0.25f }
                                )

                                SwipeToDismissBox(
                                    state = dismissState,
                                    //                                modifier = Modifier.animateItemPlacement(), // For smoother animations on removal
                                    enableDismissFromStartToEnd = false, // Disable swipe from left to right
                                    enableDismissFromEndToStart = true,  // Enable swipe from right to left
                                    backgroundContent = {
                                        val color by animateColorAsState(
                                            targetValue = when (dismissState.targetValue) {
                                                SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(
                                                    alpha = 0.8f
                                                )

                                                else -> Color.Transparent // Or a less prominent color for other states
                                            }, label = "background color"
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(
                                                        color,
                                                        shape = RoundedCornerShape(12.dp)
                                                    )
                                                    .padding(horizontal = 16.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                Icon(
                                                    Icons.Filled.Delete,
                                                    contentDescription = "Delete Icon",
                                                    tint = Color.White
                                                )
                                            }
                                        }

                                    }
                                )
                                {
                                    TaskCard(
                                        task,
                                        onClick = {
                                            println("task clicked: ${task.id}")
                                            navController.navigate(Routes.TaskDetail.name + "/" + task.id)
                                        }
                                    )
                                }


                            }
                        }


                        // Confirmation Dialog
                        if (showConfirmationDialog && itemToConfirmDelete != null) {
                            AlertDialog(
                                onDismissRequest = {
                                    // Dismiss the dialog if the user clicks outside or presses back
                                    showConfirmationDialog = false
                                    itemToConfirmDelete = null
                                },
                                title = { Text("Confirm Deletion") },
                                text = {
                                    Text(
                                        "Are you sure you want to delete '${
                                            itemToConfirmDelete?.id
                                        }'?"
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            itemToConfirmDelete?.let {
//                                                removeItem(it) // Perform the actual deletion
                                            }
                                            showConfirmationDialog = false
                                            itemToConfirmDelete = null
                                        }
                                    ) {
                                        Text("Delete")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            showConfirmationDialog = false
                                            itemToConfirmDelete = null
                                        }
                                    ) {
                                        Text("Cancel")
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete Icon"
                                    )
                                },
                                containerColor = MaterialTheme.colorScheme.surfaceVariant, // Optional: Dialog theming
                                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
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