package com.book.management.ui.list

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.book.management.MainActivity
import com.book.management.R
import com.book.management.bean.BookBean
import com.book.management.ext.res
import com.book.management.tools.LogUtil
import com.book.management.tools.toast
import com.book.management.ui.theme.Red2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainActivity.ListPage(navController: NavHostController) {
    val viewModel by remember { viewModels<ListViewModel>() }
    var bookList by remember { mutableStateOf(mutableStateListOf<BookBean>()) }

    LaunchedEffect(viewModel) {
        viewModel.eventFlow.onEach {
            when (it) {
                is ListEvent.GetBookList -> {
                    bookList.clear()
                    bookList.addAll(it.bookList)
                }

                is ListEvent.Error -> {
                    toast("interface error")
                }
            }
        }.launchIn(this)
        viewModel.dispatch(ListAction.UpdateList)
    }
    var refreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            viewModel.dispatch(ListAction.UpdateList)
            delay(1000)
            refreshing = false
        }
    })
    Box(Modifier
        .fillMaxSize()
        .pullRefresh(state)) {
        LazyColumn(Modifier
            .fillMaxWidth()
            .background(Color.LightGray)) {
            item {
                Box(Modifier
                    .padding(1.dp)
                    .background(Color.White)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    Text(text = R.string.refresh.res(), color = MaterialTheme.colorScheme.primary)
                }
            }
            items(bookList.size) {
                ListItem(navController, bookList[it])
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}


@Composable
private fun MainActivity.ListItem(navController: NavHostController, bookBean: BookBean) {
    Row(Modifier
        .height(80.dp)
        .padding(1.dp)
        .clickable {}
        .background(Color.White),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.AccountBox, contentDescription = null, modifier = Modifier.size(60.dp), tint = Red2)
        Column(Modifier
            .padding(10.dp, 0.dp)
            .weight(1f)) {
            Text(text = bookBean.name,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                fontSize = TextUnit(14f, TextUnitType.Sp))
            Spacer(modifier = Modifier.size(3.dp))
            Text(text = bookBean.author,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = TextUnit(10f, TextUnitType.Sp))
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {
            currentBook = bookBean
            navController.navigate(MainActivity.ROUTE_DETAIL)
        }) {
            Text(text = stringResource(R.string.more))
        }
    }
}
