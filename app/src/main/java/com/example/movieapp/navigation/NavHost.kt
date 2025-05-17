package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
   NavHost(navController = navController, startDestination = "Banner Screen") {
       composable("Banner Screen"){
            BannerScreen(navController = navController)
       }
       composable("Home Screen"){
           HomeScreen(navController = navController)
       }
       composable("Details Screen/{id}",
           arguments = listOf(
               navArgument(
                   name = "id"
               ){
                   type = NavType.IntType
               }
           )
       ){id->
           id.arguments?.getInt("id")?.let{id1->
               DetailsScreen(id =id1 )
       }

       }
   }
}




