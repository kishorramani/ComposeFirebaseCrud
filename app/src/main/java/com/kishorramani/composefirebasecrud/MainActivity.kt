package com.kishorramani.composefirebasecrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import com.kishorramani.composefirebasecrud.ui.theme.ComposeFirebaseCRUDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            ComposeFirebaseCRUDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToDoApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ToDoApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "display"
    ) {
        composable("display") {
            DisplayToDoScreen(navHostController = navController)
        }
        composable("add") {
            AddToDoScreen(navHostController = navController)
        }
        composable(
            route = "edit/{id}/{title}/{description}/{isDone}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("isDone") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val args = backStackEntry.arguments!!
            EditToDoScreen(
                navHostController = navController,
                ToDo(
                    id = args.getString("id") ?: "",
                    title = args.getString("title") ?: "",
                    description = args.getString("description") ?: "",
                    isDone = args.getInt("isDone") ?: 0,
                )
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeFirebaseCRUDTheme {
        Greeting("Android")
    }
}