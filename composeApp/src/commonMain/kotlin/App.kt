import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kmpexample.composeapp.generated.resources.Res
import kmpexample.composeapp.generated.resources.compose_multiplatform
import kmpexample.composeapp.generated.resources.icons8_success
import org.jetbrains.compose.resources.painterResource

@Composable
fun App(navController: NavHostController) {
    MaterialTheme {
        println("Render whole UI")
        var showContent by remember { mutableStateOf(false) }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val newAge = rememberSaveable { mutableStateOf<String>("") }
            val suggestionsList =
                rememberSaveable { mutableStateOf<List<SuggestedChipDetails>>(mutableListOf()) }
            val chipList by rememberSaveable {
                mutableStateOf<MutableList<SuggestedChip>>(mutableListOf())
            }
            RebuggerConfig.init(tag = "Custom view")
            CustomTextInputLayout2(
                hintText = "Age",
                hintColor1 = N500,
                hintTextStyle = TextStyle.Default,
                inputTextState = newAge,
                gridView = true,
                suggestedItemList = suggestionsList,
                inputTextClicked = {
                    newAge.value = "18yrs-20yrs"
                    suggestionsList.value = fetchSuggestions()

                },
                suggestedChipClicked = { data ->
                    newAge.value = if (data.isSelected) {
                        chipList.add(data)
                        var ans = ""
                        var count = 0
                        for (item in chipList) {
                            if (count > 2) {
                                ans += "+" + (chipList.size - count) + "more"
                                break
                            }
                            ans += item.clickedChipDetails.value

                            if (count != chipList.size - 1)
                                ans += " , "
                            count++

                        }
                        ans
                    } else {
                        var ans = ""
                        var count = 0
                        var index = 0
                        for (item in chipList) {
                            if (item.clickedChipDetails.label == data.clickedChipDetails.label) {
                                index = count
                            }
                            count++
                        }
                        count = 0
                        chipList.removeAt(index)
                        for (item in chipList) {
                            if (count > 2) {
                                ans += "+" + (chipList.size - count) + "more"
                                break
                            }
                            if (item.isSelected) {
                                ans += item.clickedChipDetails.value
                                if (count != chipList.size - 1)
                                    ans += " , "
                            }
                            count++
                        }
                        if (chipList.isEmpty()) {
                            ""
                        } else
                            ans
                    }
                    fetchMoreList(data, suggestionsList)
                },
                clickedLeftIcon = Res.drawable.icons8_success,
                chipsTextModifier = Modifier.padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
                drawableLeftModifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ), multiChipSelection = true
            )


            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
//            JsSnackBar("Custom snackbar message", actionLabel = "Click me", onLabelClicked = {
//                println("Clicked")
//            })
            var loading = remember { mutableStateOf(false) }
            JsButton(
                textColor = ButtonState.FILLED.textColor(),
                buttonText = "Ok",
                onClicked = {
                    loading.value = !loading.value
                },
                typeOfButtonState = ButtonState.FILLED,
                backGroundColor = ButtonState.FILLED.backGroundColor()
            )

            CustomButtonNew(
                backGroundColor = Color.Red,
                buttonText = "Click me",
                typeOfButtonState = ButtonState.OUTLINED,
                textColor = Color.Black,
                outLineBorderColor = Color.Black,
                loaderColor = Color.Red,
                isLoading = loading.value,
                fullWidth = true,
                textStyle = MaterialTheme.typography.body1
            ) {
                navController.navigate("App2")
            }
        }
    }

}

fun fetchSuggestions(): List<SuggestedChipDetails> {
    return listOf(
        SuggestedChipDetails(
            label = "1",
            value = "22yrs-24yrs",
        ),
        SuggestedChipDetails(
            label = "2",
            value = "24yrs-26yrs"
        ),
        SuggestedChipDetails(
            label = "3",
            value = "26yrs-28yrs"
        ),
        SuggestedChipDetails(
            label = "4",
            value = "28yrs-30yrs"
        ),
        SuggestedChipDetails(
            label = "5",
            value = "30yrs-32yrs"
        ),
        SuggestedChipDetails(
            label = "6",
            value = "32yrs-34yrs"
        ),
        SuggestedChipDetails(
            label = "7",
            value = "34yrs-36yrs"
        )
    )

}

fun fetchCities(): List<SuggestedChipDetails> {
    return listOf(
        SuggestedChipDetails(
            label = "1",
            value = "Ghaziabad",
        ),
        SuggestedChipDetails(
            label = "2",
            value = "Delhi"
        ),
        SuggestedChipDetails(
            label = "3",
            value = "Noida"
        ),
        SuggestedChipDetails(
            label = "4",
            value = "Greater Noida"
        ),
        SuggestedChipDetails(
            label = "5",
            value = "Gurugram"
        ),
        SuggestedChipDetails(
            label = "6",
            value = "Faridabad"
        ),
        SuggestedChipDetails(
            label = "7",
            value = "Harayana"
        )
    )

}

fun fetchMoreList(data: SuggestedChip, suggestionsList: MutableState<List<SuggestedChipDetails>>) {
    val inputText = data.inputText
    val clickedChip = data.clickedChipDetails

}
