/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.recipe.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.recipe.inventory.AddDataDestination
import com.recipe.inventory.AddDataScreen
import com.recipe.inventory.GetDataDestination
import com.recipe.inventory.GetDataScreen
import com.recipe.inventory.LoginDestination
import com.recipe.inventory.LoginScreen
import com.recipe.inventory.SplashDestination
import com.recipe.inventory.SplashScreen

import com.recipe.inventory.ui.home.HomeDestination
import com.recipe.inventory.ui.home.HomeScreen
import com.recipe.inventory.ui.item.ItemDetailsDestination
import com.recipe.inventory.ui.item.ItemDetailsScreen
import com.recipe.inventory.ui.item.ItemEditDestination
import com.recipe.inventory.ui.item.ItemEditScreen
import com.recipe.inventory.ui.item.ItemEntryDestination
import com.recipe.inventory.ui.item.ItemEntryScreen


/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,

) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(AddDataDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }



        composable(route = AddDataDestination.route) {
            AddDataScreen(
                navigateBack = { navController.popBackStack() },
                navigateToTaskEntry = { navController.navigate(GetDataDestination.route) }

            )
        }

        composable(route = GetDataDestination.route) {
            GetDataScreen(
                navigateBack = { navController.popBackStack() }
               // navigateToTaskEntry = { navController.navigate(TaskDestination.route) }

            )
        }

        composable(route = SplashDestination.route) {
            SplashScreen(
                navigateToTaskEntry = { navController.navigate(HomeDestination.route) },
                navigateToTaskLogin = { navController.navigate(LoginDestination.route) }

            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToTaskEntry = { navController.navigate(HomeDestination.route) }

            )
        }



        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }





    }
}
