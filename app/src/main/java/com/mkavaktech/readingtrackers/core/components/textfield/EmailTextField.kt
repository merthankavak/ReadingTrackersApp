import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mkavaktech.readingtrackers.core.components.textfield.InputField

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelText: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    leadingIcon: ImageVector = Icons.Rounded.Email
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelText = labelText,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
        leadingIcon = leadingIcon,
    )
}
