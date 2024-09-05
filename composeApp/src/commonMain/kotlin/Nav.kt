import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Created by Himanshu Verma on 02/08/24.
 **/
val enterTransition: EnterTransition =
    slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500)) +
            fadeIn(animationSpec = tween(500))

val exitTransition: ExitTransition =
    slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500)) +
            fadeOut(animationSpec = tween(500))

val popEnterTransition: EnterTransition =
    slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500)) +
            fadeIn(animationSpec = tween(500))

val popExitTransition: ExitTransition =
    slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500)) +
            fadeOut(animationSpec = tween(500))

@Composable
fun Nav() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "App") {

        composable(route = "App",
            enterTransition = {
                enterTransition
            },
            exitTransition = {
                exitTransition
            },
            popEnterTransition = {
                popEnterTransition
            },
            popExitTransition = {
                popExitTransition
            }
        ) {
            App(navController)
        }

        composable(route = "App2",
            enterTransition = {
                enterTransition
            },
            exitTransition = {
                exitTransition
            },
            popEnterTransition = {
                popEnterTransition
            },
            popExitTransition = {
                popExitTransition
            }) {
            App2(navController)
        }

    }

}