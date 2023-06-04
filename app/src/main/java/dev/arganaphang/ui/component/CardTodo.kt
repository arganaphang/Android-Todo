package dev.arganaphang.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.arganaphang.todolist.data.Todo
import dev.arganaphang.ui.theme.TodoListTheme
import dev.arganaphang.utils.ralativeDateFromNow
import java.util.Date


@Composable
fun CardTodo(todo: Todo, onUpdate: (Long) -> Unit) {
    var showContextMenu by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        showContextMenu = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(todo.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    ralativeDateFromNow(todo.createdAt),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
                    .clip(CircleShape)
                    .background((if (todo.isDone) Color.Green else Color.Red).copy(alpha = .2f))
            )
        }
        DropdownMenu(
            expanded = showContextMenu,
            onDismissRequest = { showContextMenu = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        if (todo.isDone)
                            "Not Done"
                        else
                            "Done",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.DarkGray)
                    )
                },
                onClick = {
                    onUpdate(todo.id)
                    showContextMenu = false
                }
            )

        }
    }
}

@Preview
@Composable
fun CardTodoPreview() {
    TodoListTheme {
        Surface {
            CardTodo(
                todo = Todo(1, "First Todo", true, Date())
            ) {}
        }
    }
}