package com.example.finalproject.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalproject.data.model.PostCategory
import com.example.finalproject.ui.components.PopUpDialog
import com.example.finalproject.ui.theme.FinalProjectTheme
import com.example.finalproject.ui.theme.darkBackground
import com.example.finalproject.ui.theme.darkgrey
import com.example.finalproject.ui.theme.green
import com.example.finalproject.ui.theme.grey
import com.example.finalproject.ui.theme.red
import com.example.finalproject.ui.theme.white
import com.example.finalproject.util.Utils


@Composable
fun PostCreateScreen(
    navController: NavController,
    onPostCreate: (Context, String, String, String) -> Unit
) {
    val context = LocalContext.current

    var selectedPostCategory by remember { mutableStateOf("") }
    var postTitle by remember { mutableStateOf("") }
    var postContent by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {PostCreateTopBar(navController = navController)},
        bottomBar = {Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PostCreateButton(
                modifier = Modifier,
                enabled = selectedPostCategory.isNotEmpty() and postTitle.isNotEmpty() and postContent.isNotEmpty(),
                navController, context, selectedPostCategory, postTitle, postContent, onPostCreate
            )
        }}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(darkBackground)
                .verticalScroll(rememberScrollState())
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        keyboardController?.hide()
                    },
                )
        ) {
            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            Text(
                text = "Category: ",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                items(PostCategory.values()) {category ->
                    val selected = (selectedPostCategory == category.value)
                    Button(
                        onClick = {selectedPostCategory = category.value},
                        border = ButtonDefaults.outlinedButtonBorder,
                        shape = RoundedCornerShape(8),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) darkgrey else Color.Transparent,
                            contentColor =  if (selected) green else white
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) { Text(text = category.value)}

                }
            }


            Spacer(     // horizontal divisor
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            Text(
                text = "Title: ",
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                OutlinedTextField(
                    value = postTitle,
                    onValueChange = {postTitle = it},
                    placeholder = { Text(text = "Write your title here...", color = MaterialTheme.colorScheme.secondary)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = darkBackground,
                        unfocusedContainerColor = darkBackground,
                        unfocusedTextColor = grey,
                        focusedTextColor = grey,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )
//                PostCategory.values().forEach { category ->
//                    Text(text = category.value)
//                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            Text(text = "Content: ", modifier = Modifier.padding(10.dp), fontSize=16.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                OutlinedTextField(
                    value = postContent,
                    onValueChange = {postContent = it},
                    placeholder = { Text(text = "Write your post here...")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(250.dp),
//                    shape = RoundedCornerShape(5),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = darkBackground,
                        unfocusedContainerColor = darkBackground,
                        unfocusedTextColor = grey,
                        focusedTextColor = grey,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )
//                PostCategory.values().forEach { category ->
//                    Text(text = category.value)
//                }
            }
//            PostCreateButton(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                enabled = selectedPostCategory.isNotEmpty(),
//                context, selectedPostCategory, postTitle, postContent, onPostCreate
//            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreateTopBar(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = darkBackground,
            titleContentColor = white,
        ),
        title = {
            Text(
                text = "New Post",
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
//                    navController.navigateUp()
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = white
                )

                if (showDialog) {
                    PopUpDialog(
                        title = "Discard Writings",
                        body = "Are you sure you want to go back to the main page? (All you have written will be lost.",
                        actionText = "Yes",
                        onClose = {
                            showDialog = false
                        },
                        onDismiss = {
                            navController.navigateUp()
                            showDialog = false // Hide the delete dialog
                        }
                    )
                }
            }
        },
    )
}

@Composable
fun PostCreateButton(
    modifier: Modifier, enabled: Boolean,
    navController: NavController,
    context: Context, category: String, title: String, content: String,
    onPostCreate: (Context, String, String, String) -> Unit
)  {
    Button(
        onClick = {
            onPostCreate(context, category, title, content)
            navController.navigateUp()
            Utils.showMessage(context, "Post Successful")
        },
        modifier = modifier
            .padding(bottom = 40.dp)
            .width(300.dp)
            .height(48.dp),
//            .align(Alignment.CenterHorizontally),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = green,
            disabledContainerColor = grey
        ),
        enabled = enabled
    ) {
        Text(
            text = "Post",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W600,
            color = white,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PostCreateScreenPreview() {
    FinalProjectTheme(darkTheme = true) {
        PostCreateScreen(NavController(LocalContext.current), {a, b, c, d ->})
    }
}
