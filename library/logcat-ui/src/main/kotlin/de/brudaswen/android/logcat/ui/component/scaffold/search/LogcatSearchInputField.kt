package de.brudaswen.android.logcat.ui.component.scaffold.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun LogcatSearchInputField(
    textFieldState: TextFieldState,
    searchBarState: SearchBarState,
    placeholder: String,
    enabled: Boolean = true,
    onSearch: (String) -> Unit,
    onCloseClick: (() -> Unit)?,
) {
    SearchBarDefaults.InputField(
        textFieldState = textFieldState,
        searchBarState = searchBarState,
        leadingIcon = {
            Icon(Icons.Default.Search, null)
        },
        placeholder = {
            Text(placeholder)
        },
        trailingIcon = {
            onCloseClick?.let {
                IconButton(
                    onClick = onCloseClick,
                ) {
                    Icon(Icons.Rounded.Close, null)
                }
            }
        },
        onSearch = onSearch,
    )
}
