package com.book.management.ui.detail

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.book.management.MainActivity
import com.book.management.R
import com.book.management.bean.BookBean
import com.book.management.ext.isNullOrZero
import com.book.management.ext.res
import com.book.management.tools.toast
import com.book.management.ui.create.CreateEvent
import com.book.management.ui.create.CreateViewModel
import com.book.management.widget.LimitedNumberTextField
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MainActivity.DetailPage(navController: NavHostController) {
    val viewModel by remember { viewModels<DetailViewModel>() }
    var bookId by remember { mutableStateOf("Book ID: ${currentBook?.bookId ?: 0}") }
    var name by remember { mutableStateOf(currentBook?.name ?: "") }
    var author by remember { mutableStateOf(currentBook?.author ?: "") }
    var year by remember { mutableStateOf(TextFieldValue(currentBook?.year?.toString() ?: "1813")) }
    var isbn by remember { mutableStateOf(currentBook?.isbn ?: "") }

    LaunchedEffect(viewModel) {
        viewModel.eventFlow.onEach {
            when (it) {
                is DetailEvent.DeleteSuccess -> {
                    toast("delete success")
                    navController.popBackStack()
                }

                is DetailEvent.Error -> {
                    toast(it.msg)
                }

                is DetailEvent.UpdateSuccess -> {
                    toast("update success")
                }
            }
        }.launchIn(this)
    }
    Column(Modifier
        .fillMaxSize()
        .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = R.string.book_detail.res(),
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(fontWeight = FontWeight.W800, fontSize = 20.sp, letterSpacing = 7.sp))
        }

        Text(text = bookId,
            modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = TextUnit(20f, TextUnitType.Sp))

        TextField(value = name, onValueChange = {
            name = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), singleLine = true, label = {
            Text(R.string.input_name.res())
        })
        TextField(value = author, onValueChange = {
            author = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp, 30.dp, 0.dp), singleLine = true, label = {
            Text(R.string.input_author.res())
        })
        LimitedNumberTextField(
            value = year,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 0.dp),
            label = {
                Text(R.string.input_year_e_g_2012.res())
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
            Text(R.string.input_isbn.res())
        })
        Spacer(modifier = Modifier.weight(1f))
        Row(Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.padding(5.dp), onClick = {
                currentBook?.bookId?.let {
                    viewModel.dispatch(DetailAction.Update(BookBean(bookId = it,
                        name = name,
                        author = author,
                        year = year.text.toInt(),
                        isbn = isbn)))
                }
            }) {
                Text(text = R.string.update.res())
            }
            Button(modifier = Modifier.padding(5.dp), onClick = {
                currentBook?.bookId?.let {
                    viewModel.dispatch(DetailAction.Delete(it))
                }
            }) {
                Text(text = R.string.delete.res())
            }
            Button(modifier = Modifier.padding(5.dp), onClick = {
                navController.popBackStack()
            }) {
                Text(text = R.string.close.res())
            }
        }
    }
}
