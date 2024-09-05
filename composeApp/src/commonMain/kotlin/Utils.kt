import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Himanshu Verma on 17/07/24.
 **/
enum class ButtonState {
    FILLED,
    NOT_FILLED,
    OUTLINED
}

enum class TYPE {
    AGE,
    HEIGHT,
    COUNTRY
}

data class SuggestedChip(
    val inputText: String,
    val clickedChipDetails: SuggestedChipDetails,
    val isSelected: Boolean = false,
)

data class SuggestedChipDetails(
    val label: String,
    val value: String,
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextInputLayout2(
    hintText: String,
    hintColor1: Color = N500,
    hintTextStyle: TextStyle = TextStyle.Default,
    inputTextClicked: () -> Unit,
    inputTextStyle1: TextStyle = TextStyle.Default.copy(color = N400),
    inputTextState: MutableState<String>,
    suggestedItemList: MutableState<List<SuggestedChipDetails>>,
    suggestedTitle: String? = null,
    gridView: Boolean = false,
    iconRight: DrawableResource? = null,
    iconLeft: DrawableResource? = null,
    clickedRightIcon: DrawableResource? = null,
    clickedLeftIcon: DrawableResource? = null,
    suggestedChipClicked: (data: SuggestedChip) -> Unit,
    chipsTextModifier: Modifier,
    drawableLeftModifier: Modifier = Modifier,
    drawableRightModifier: Modifier = Modifier,
    multiChipSelection: Boolean = false,
    dividerColor1: Color = Color.Gray,
) {
    println("Render Main layout")
    val interactionSource = remember { MutableInteractionSource() }
    val input = remember { inputTextState }

    val hintColor = remember { mutableStateOf((hintColor1)) }
    val dividerColor = remember { mutableStateOf((dividerColor1)) }
    val inputTextStyle = remember { mutableStateOf((inputTextStyle1)) }



    if (interactionSource.collectIsPressedAsState().value)
        inputTextClicked()
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp))
    {
        BasicTextField(
            value = input.value,
            onValueChange = {

            },
            modifier = Modifier.fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        inputTextClicked()
                    }
                ),
            readOnly = true,
            textStyle = inputTextStyle.value,
            singleLine = true,
            interactionSource = interactionSource,
        ) { innerTextField ->

            TextFieldDefaults.TextFieldDecorationBox(
                value = input.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = {
                    Box(modifier = Modifier.padding(top = 4.dp)) {
                        innerTextField()
                    }
                },
                singleLine = true,
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = 12.dp),
                        text = hintText,
                        style = hintTextStyle,
                        fontWeight = FontWeight.Medium,
                        color = hintColor.value
                    )
                },
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 12.dp
                ),
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(dividerColor.value)
        )


        if (suggestedItemList.value.isNotEmpty()) {
            Spacer(modifier = Modifier.padding(10.dp))


            suggestedChips(
                suggestedTitle,
                suggestedItemList,
                suggestedChipClicked = {
                    suggestedChipClicked(it)
                },
                input.value,
                gridView,
                iconRight,
                iconLeft,
                clickedLeftIcon,
                clickedRightIcon,
                chipsTextModifier,
                drawableLeftModifier,
                drawableRightModifier,
                multiChipSelection
            )
        }
    }
}

/**
 *
 *
 *
 *  Rebugger(
 *         trackMap = mapOf(
 *             "hintText" to hintText,
 *             "hintColor" to hintColor,
 *             "hintTextStyle" to hintTextStyle,
 *             "inputTextClicked" to inputTextClicked,
 *             "inputTextStyle" to inputTextStyle,
 *             "suggestitemlist" to suggestedItemList,
 *             "suggestChipsTitle" to suggestedTitle,
 *             "inputText" to inputTextState,
 *             "suggestedChipClicked" to suggestedChipClicked,
 *             "suggestedItemInGridCells" to gridView,
 *             "suggestedItemsDrawableRight" to iconRight,
 *             "suggestedItemsDrawableLeft" to iconLeft,
 *             "clickedLeftIcon" to clickedLeftIcon,
 *             "clickedRightIcon" to clickedRightIcon,
 *             "chipsTextModifier" to chipsTextModifier,
 *             "drawableLeftModifier" to drawableLeftModifier,
 *             "drawableRightModifier" to drawableRightModifier,
 *             "multiSelect" to multiChipSelection,
 *             "dividerColor" to dividerColor,
 *             "interactionSource" to interactionSource,
 *             "input" to input
 *         )
 *     )
 *
 *
 *  Rebugger(
 *         trackMap = mapOf(
 *             "suggestitemlist" to suggestions,
 *             "suggestChipsTitle" to suggestChipsTitle,
 *             "inputText" to inputText,
 *             "suggestedChipClicked" to suggestedChipClicked,
 *             "suggestedItemInGridCells" to suggestedItemInGridCells,
 *             "suggestedItemsDrawableRight" to suggestedItemsDrawableRight,
 *             "suggestedItemsDrawableLeft" to suggestedItemsDrawableLeft,
 *             "clickedLeftIcon" to clickedLeftIcon,
 *             "clickedRightIcon" to clickedRightIcon,
 *             "chipsTextModifier" to chipsTextModifier,
 *             "drawableLeftModifier" to drawableLeftModifier,
 *             "drawableRightModifier" to drawableRightModifier,
 *             "multiSelect" to multiSelect
 *         )
 *     )
 *
 *
 *
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun suggestedChips(
    suggestChipsTitle: String?,
    suggestions: MutableState<List<SuggestedChipDetails>>,
    suggestedChipClicked: (data: SuggestedChip) -> Unit,
    inputText: String,
    suggestedItemInGridCells: Boolean = false,
    suggestedItemsDrawableRight: DrawableResource?,
    suggestedItemsDrawableLeft: DrawableResource?,
    clickedLeftIcon: DrawableResource?,
    clickedRightIcon: DrawableResource?,
    chipsTextModifier: Modifier,
    drawableLeftModifier: Modifier,
    drawableRightModifier: Modifier,
    multiSelect: Boolean = false,
) {


    println("Render Chip layout")
    var selectedIndex by rememberSaveable {
        mutableStateOf(-1)
    }
    var selectedIndices by rememberSaveable { mutableStateOf(setOf<Int>()) }
    val suggestedItemList = rememberSaveable { suggestions }
    val interactionSource = remember { MutableInteractionSource() }


    val chipContent: @Composable (Int) -> Unit = { index ->
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (suggestedItemsDrawableLeft != null) {
                IconToBeDisplayed(
                    multiSelect,
                    selectedIndices,
                    index,
                    clickedLeftIcon,
                    suggestedItemsDrawableLeft,
                    selectedIndex,
                    drawableLeftModifier
                )
            }
            Text(
                text = suggestedItemList.value[index].value,
                style = TextStyle.Default,
                color = if (multiSelect)
                    if (selectedIndices.contains(index))
                        P500
                    else
                        NG800
                else
                    if (selectedIndex == index)
                        P500
                    else
                        NG800,
                modifier = chipsTextModifier,
                maxLines = 1
            )
            if (suggestedItemsDrawableRight != null) {
                IconToBeDisplayed(
                    multiSelect,
                    selectedIndices,
                    index,
                    clickedRightIcon,
                    suggestedItemsDrawableRight,
                    selectedIndex,
                    drawableRightModifier
                )
            }
        }

    }
    if (suggestChipsTitle?.isEmpty() == false) {
        Text(
            text = suggestChipsTitle,
            modifier = Modifier.padding(start = 16.dp).fillMaxWidth(),
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 12.sp
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }


    if (suggestedItemInGridCells) {
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            suggestedItemList.value.forEachIndexed { index, _ ->
                val isSelected =
                    if (multiSelect) selectedIndices.contains(index) else selectedIndex == index
                val borderColor = if (isSelected) P500 else Color.Gray
                val bgColor = if (isSelected) P50 else NG100
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .clickable(
                            onClick = {
                                // Setting up index
                                if (multiSelect)
                                    selectedIndices = if (isSelected) {
                                        selectedIndices - index
                                    } else {
                                        selectedIndices + index
                                    }
                                else
                                    selectedIndex = index
                                suggestedChipClicked(
                                    SuggestedChip(
                                        inputText,
                                        suggestedItemList.value[index],
                                        !isSelected
                                    )
                                )
                            },
                            indication = null,
                            interactionSource = interactionSource
                        )

                        .border(
                            BorderStroke(1.dp, borderColor),
                            shape = RoundedCornerShape(16.dp),
                        )
                        .background(
                            bgColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    chipContent(index)
                }
            }
        }
    } else {
        LazyRow(modifier = Modifier) {
            items(suggestedItemList.value.size) { index ->
                val isSelected =
                    if (multiSelect) selectedIndices.contains(index) else selectedIndex == index
                val borderColor = if (isSelected) P500 else Color.Gray
                val bgColor = if (isSelected) P50 else NG100
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .clickable(
                            onClick = {
                                // Setting up index
                                if (multiSelect)
                                    selectedIndices = if (isSelected) {
                                        selectedIndices - index
                                    } else {
                                        selectedIndices + index
                                    }
                                else
                                    selectedIndex = index
                                suggestedChipClicked(
                                    SuggestedChip(
                                        inputText,
                                        suggestedItemList.value[index],
                                        !isSelected
                                    )
                                )
                            },
                            indication = null,
                            interactionSource = interactionSource
                        )

                        .border(
                            BorderStroke(1.dp, borderColor),
                            shape = RoundedCornerShape(16.dp),
                        )
                        .background(
                            bgColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    chipContent(index)
                }
            }
        }
    }
}

@Composable
private fun IconToBeDisplayed(
    multiSelect: Boolean,
    selectedIndices: Set<Int>,
    index: Int,
    clickedIconDrawable: DrawableResource?,
    iconDrawable: DrawableResource,
    selectedIndex: Int,
    drawableModifier: Modifier,
) {
    Image(
        painter = painterResource(
            if (multiSelect)
                if (selectedIndices.contains(index))
                    clickedIconDrawable ?: iconDrawable
                else
                    iconDrawable
            else
                if (selectedIndex == index)
                    clickedIconDrawable ?: iconDrawable
                else
                    iconDrawable
        ),
        contentDescription = null,
        modifier = drawableModifier
    )
}


fun ButtonState.textColor(): Color {
    return when (this) {
        ButtonState.FILLED -> {
            Color.White
        }

        ButtonState.NOT_FILLED, ButtonState.OUTLINED -> {
            P500
        }
    }
}

fun ButtonState.backGroundColor(): Color {
    return when (this) {
        ButtonState.FILLED -> {
            P500
        }

        ButtonState.NOT_FILLED, ButtonState.OUTLINED -> {
            Color.White
        }
    }
}

fun ButtonState.outLineBorderColor(): Color? {
    return when (this) {
        ButtonState.FILLED -> {
            null
        }

        ButtonState.NOT_FILLED, ButtonState.OUTLINED -> {
            P500
        }
    }
}


@Composable
fun JsButton(
    textColor: Color = ButtonState.FILLED.textColor(),
    textStyle: TextStyle = TextStyle.Default,
    buttonText: String,
    iconDrawable: DrawableResource? = null,
    typeOfButtonState: ButtonState = ButtonState.FILLED,
    backGroundColor: Color = ButtonState.FILLED.backGroundColor(),
    outLineBorderColor: Color? = ButtonState.FILLED.outLineBorderColor(),
    isLoading: Boolean = false,
    loaderColor: Color = P500,
    fullWidth: Boolean = false,
    buttonPadding: PaddingValues = PaddingValues(start = 20.dp, end = 20.dp),
    onClicked: () -> Unit,
) {
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }
    BoxWithConstraints(
        modifier = Modifier
            .then(
                if (fullWidth) Modifier.fillMaxWidth() else Modifier
            )
            .padding(buttonPadding)
            .then(
                if (outLineBorderColor != null && typeOfButtonState == ButtonState.OUTLINED) Modifier.border(
                    2.dp,
                    outLineBorderColor,
                    RoundedCornerShape(8.dp)
                )
                else Modifier
            )
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (typeOfButtonState == ButtonState.FILLED) Modifier.background(backGroundColor) else Modifier
            )
            .clickable(enabled = !isLoading) { onClicked() }
            .onGloballyPositioned { coordinates ->
                if (buttonSize == IntSize.Zero) {
                    buttonSize = coordinates.size
                }
            }
    ) {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        if (buttonSize != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .size(with(LocalDensity.current) {
                        buttonSize.toSize().toDpSize()
                    }
                    )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = loaderColor,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .scale(0.5f)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = buttonText,
                            color = textColor,
                            style = textStyle,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = if (iconDrawable != null) 8.dp else 16.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            )
                        )
                        if (iconDrawable != null) {
                            Icon(
                                painter = painterResource(iconDrawable),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = buttonText,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.padding(end = if (iconDrawable != null) 8.dp else 0.dp)
                )
                if (iconDrawable != null) {
                    Icon(
                        painter = painterResource(iconDrawable),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomButtonNew(
    textColor: Color,
    textStyle: TextStyle,
    buttonText: String,
    iconDrawable: DrawableResource? = null,
    typeOfButtonState: ButtonState = ButtonState.FILLED,
    backGroundColor: Color = P500,
    outLineBorderColor: Color? = null,
    isLoading: Boolean = false,
    loaderColor: Color = P500,
    fullWidth: Boolean = false,
    onClicked: () -> Unit,
) {
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }
    BoxWithConstraints(
        modifier = Modifier
            .then(
                if (fullWidth) Modifier.fillMaxWidth() else Modifier
            )
            .padding(20.dp)
            .then(
                if (outLineBorderColor != null && typeOfButtonState == ButtonState.OUTLINED) Modifier.border(
                    2.dp,
                    outLineBorderColor,
                    RoundedCornerShape(8.dp)
                )
                else Modifier
            )
            .clip(RoundedCornerShape(8.dp))
            .then(
                if (typeOfButtonState == ButtonState.FILLED) Modifier.background(backGroundColor) else Modifier
            )
            .clickable(enabled = !isLoading) { onClicked() }
            .onGloballyPositioned { coordinates ->
                if (buttonSize == IntSize.Zero) {
                    buttonSize = coordinates.size
                }
            }
    ) {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        if (buttonSize != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .size(with(LocalDensity.current) {
                        buttonSize.toSize().toDpSize()
                    }
                    )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = loaderColor,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .scale(0.5f)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = buttonText,
                            color = textColor,
                            style = textStyle,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = if (iconDrawable != null) 8.dp else 16.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            )
                        )
                        if (iconDrawable != null) {
                            Icon(
                                painter = painterResource(iconDrawable),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = buttonText,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.padding(end = if (iconDrawable != null) 8.dp else 0.dp)
                )
                if (iconDrawable != null) {
                    Icon(
                        painter = painterResource(iconDrawable),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun JsSnackBar(
    messageToBeDisplayed: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    actionLabel: String? = null,
    onLabelClicked: (() -> Unit)? = null,
    actionColor: Color = Color.Green,
    contentColor: Color = Color.Blue,
    snackBarBackgroundColor: Color = Color.White,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    contentColor = contentColor,
                    backgroundColor = snackBarBackgroundColor,
                    actionColor = actionColor,
                    snackbarData = data
                )
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            showSnackBar(
                scaffoldState = scaffoldState,
                messageToBeDisplayed = messageToBeDisplayed,
                actionLabel = actionLabel,
                duration = duration,
                onLabelClicked = onLabelClicked,
                coroutineScope = coroutineScope
            )
        }
    }
}

fun showSnackBar(
    scaffoldState: ScaffoldState,
    messageToBeDisplayed: String,
    actionLabel: String?,
    duration: SnackbarDuration,
    onLabelClicked: (() -> Unit)? = null,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = messageToBeDisplayed,
            actionLabel = actionLabel,
            duration = duration
        )
        if (result == SnackbarResult.ActionPerformed) {
            if (onLabelClicked != null) {
                onLabelClicked()
            }
        }
    }
}


// Primary Colour Scheme
val P50 = Color(0xFFFFF4F6)
val P100 = Color(0xFFFFE7EA)
val P200 = Color(0xFFFECAD2)
val P300 = Color(0xFFF098A6)
val P400 = Color(0xFFE77183)
val P500 = Color(0xFFDB485C)
val P600 = Color(0xFFC94055)
val P700 = Color(0xFFA63244)
val P800 = Color(0xFF8B2A39)
val P900 = Color(0xFF631D28)

// Neutral Colour Scheme
val NG100 = Color(0xFFFFFFFF)
val NG200 = Color(0xFFF6F7FA)
val NG300 = Color(0xFFE3E9EE)
val NG400 = Color(0xFF98A3AF)
val NG500 = Color(0xFF768493)
val NG600 = Color(0xFF617488)
val NG700 = Color(0xFF4B6177)
val NG800 = Color(0xFF34495E)
val NG900 = Color(0xFF243648)

val N500 = Color(0xFF3D4B5C)
val N400 = Color(0xFF172332)





