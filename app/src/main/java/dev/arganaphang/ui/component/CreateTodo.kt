package dev.arganaphang.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.arganaphang.ui.theme.Purple40
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodo(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxSize(),
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                placeholder = {
                    Text("Title", color = Color.LightGray)
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray
                ),
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Send,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            onSubmit(title)
                            title = ""
                        },
                        tint = Purple40
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateTodoPreview() {
    CreateTodo(
        bottomSheetState = rememberModalBottomSheetState(),
        onDismiss = {},
        onSubmit = {}
    )
}