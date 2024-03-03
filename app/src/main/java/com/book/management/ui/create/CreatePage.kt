package com.book.management.ui.create

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.book.management.MainActivity
import com.book.management.R
import com.book.management.bean.BookBean
import com.book.management.ext.res
import com.book.management.tools.toast
import com.book.management.widget.LimitedNumberTextField
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MainActivity.CreatePage(navController: NavHostController) {
    val viewModel by remember { viewModels<CreateViewModel>() }
    var name by remember { mutableStateOf("Pride and Prejudice") }
    var author by remember { mutableStateOf("Austen") }
    var isbn by remember { mutableStateOf("3-540-98465-8") }
    var year by remember { mutableStateOf(TextFieldValue("1813")) }

    LaunchedEffect(viewModel) {
        viewModel.eventFlow.onEach {
            when (it) {
                is CreateEvent.Success -> {
                    toast("commit success , bookId: ${it.bookId}")
                }

                is CreateEvent.Error -> {
                    toast(it.msg)
                }
            }
        }.launchIn(this)
    }

    Column(Modifier.padding(10.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = R.string.create_book.res(),
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(fontWeight = FontWeight.W800, fontSize = 20.sp, letterSpacing = 7.sp))
        }
        TextField(value = name, onValueChange = {
            name = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), singleLine = true, label = {
            Text(stringResource(R.string.input_name))
        })
        TextField(value = author, onValueChange = {
            author = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), singleLine = true, label = {
            Text(stringResource(R.string.input_author))
        })
        LimitedNumberTextField(
            value = year,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            label = {
                Text(stringResource(R.string.input_year_e_g_2012))
            },
            onValueChange = {
                year = it
            },
        )
        TextField(value = isbn, onValueChange = {
            isbn = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), singleLine = true, label = {
            Text(stringResource(R.string.input_isbn))
        })
        Spacer(modifier = Modifier.weight(1f))
        Button(modifier = Modifier.padding(30.dp), onClick = {
            viewModel.dispatch(CreateAction.CreateBook(BookBean(name = name,
                author = author,
                year = year.text.toInt(),
                isbn = isbn)))
        }) {
            Text(text = "commit")
        }
    }
}
