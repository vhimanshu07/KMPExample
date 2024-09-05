import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Created by Himanshu Verma on 02/08/24.
 **/


@Composable
fun App2(navController: NavHostController) {
    Column {
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Navigate to backScree")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Screen 2", fontSize = 64.sp)
    }

}