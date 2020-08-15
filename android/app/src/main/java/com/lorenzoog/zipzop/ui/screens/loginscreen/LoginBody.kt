package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.input.VisualTransformation
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.OutlinedButton
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp

@Composable
fun LoginBody() {
  var username by state { TextFieldValue("") }
  var password by state { TextFieldValue("") }

  LoginInput(
    placeholder = "Username",
    value = username,
    onValueChange = { username = it }
  )

  Spacer(modifier = Modifier.padding(14.dp))

  LoginInput(
    placeholder = "Password",
    value = password,
    onValueChange = { password = it },
    visualTransformation = PasswordVisualTransformation()
  )

  Spacer(modifier = Modifier.padding(12.dp))

  LoginButton(
    username = username.text,
    password = password.text
  )

  Spacer(modifier = Modifier.padding(12.dp))

  RegisterButton()

  Spacer(modifier = Modifier.padding(12.dp))

  ForgotPassword()
}

@Composable
private fun LoginInput(
  value: TextFieldValue,
  onValueChange: (TextFieldValue) -> Unit,
  placeholder: String? = null,
  keyboardType: KeyboardType = KeyboardType.Text,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier +
      Modifier
        .fillMaxWidth()
        .padding(horizontal = 60.dp)
        .gravity(Alignment.CenterHorizontally)
  ) {
    if (placeholder != null) {
      Text(
        modifier = Modifier.padding(vertical = 8.dp),
        style = TextStyle(
          color = MaterialTheme.colors.primary,
          fontSize = TextUnit.Em(4)
        ),
        text = placeholder
      )
    }

    TextField(
      modifier = Modifier
        .padding(8.dp)
        .heightIn(20.dp, 48.dp),
      value = value,
      onValueChange = onValueChange,
      keyboardType = keyboardType,
      visualTransformation = visualTransformation,
      imeAction = ImeAction.Search,
      textStyle = TextStyle(
        color = MaterialTheme.colors.onSurface,
        fontSize = TextUnit.Em(5)
      )
    )
  }


  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(0.8.dp)
      .drawBackground(Color(0xFF8A8A8A))
  )
}

@Composable
private fun LoginButton(username: String, password: String) {
  Button(
    modifier = Modifier
      .defaultMinSizeConstraints(300.dp)
      .gravity(Alignment.CenterHorizontally),
    padding = InnerPadding(all = 12.dp),
    shape = RoundedCornerShape(50),
    onClick = {
      TODO("Login")
    }
  ) {
    Text(
      style = TextStyle(
        fontSize = TextUnit.Em(4.5),
        fontWeight = FontWeight.Bold
      ),
      text = "LOGIN"
    )
  }
}

@Composable
private fun RegisterButton() {
  OutlinedButton(
    modifier = Modifier
      .defaultMinSizeConstraints(300.dp)
      .gravity(Alignment.CenterHorizontally),
    border = Border(1.5.dp, MaterialTheme.colors.primary),
    contentColor = MaterialTheme.colors.primary,
    padding = InnerPadding(all = 12.dp),
    shape = RoundedCornerShape(50),
    onClick = {
      TODO("Send to register page")
    }
  ) {
    Text(
      style = TextStyle(
        fontSize = TextUnit.Em(4.5),
        fontWeight = FontWeight.Bold
      ),
      text = "REGISTER"
    )
  }
}

@Composable
private fun ForgotPassword() {
  Text(
    modifier = Modifier
      .gravity(Alignment.CenterHorizontally)
      .clickable {
        TODO("Send to forgot password screen")
      },
    style = TextStyle(
      fontSize = TextUnit.Em(3.5)
    ),
    text = "Forgot your password?"
  )
}

