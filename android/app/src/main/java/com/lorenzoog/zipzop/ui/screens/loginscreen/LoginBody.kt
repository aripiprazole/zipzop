package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.state
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

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
    modifier = modifier then
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
      ),
      label = {
        Text(placeholder!!)
      }
    )
  }


  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(0.8.dp)
      .background(Color(0xFF8A8A8A))
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

