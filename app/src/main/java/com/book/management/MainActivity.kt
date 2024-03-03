package com.book.management

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.book.management.base.BaseActivity
import com.book.management.bean.BookBean
import com.book.management.ext.res
import com.book.management.ui.create.CreatePage
import com.book.management.ui.detail.DetailPage
import com.book.management.ui.list.ListPage
import com.book.management.ui.theme.ComposeBookTheme

class MainActivity : BaseActivity() {

    var currentBook: BookBean? = null

    companion object {
        const val ROUTE_MAIN = "mainPage"
        const val ROUTE_DETAIL = "detailPage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBookTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "mainPage") {
                    composable(ROUTE_MAIN) {
                        appScaffold(navController)
                    }
                    composable(ROUTE_DETAIL) {
                        DetailPage(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun appScaffold(navController: NavHostController) {
        var selectedItem by remember { mutableStateOf(1) }
        val items = listOf(R.string.create.res(), R.string.list.res())
        val images = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Settings, Icons.Filled.Face)
        Scaffold(topBar = {
            Text(R.string.book_management.res(),
                Modifier
                    .background(Color(0x557D5260))
                    .fillMaxWidth()
                    .requiredHeightIn()
                    .padding(8.dp),
                textAlign = TextAlign.Center)
        }, bottomBar = {
            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background, elevation = (0.5).dp) {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(icon = {
                        Icon(images[index],
                            contentDescription = null,
                            tint = if (selectedItem == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                    }, label = {
                        Text(item,
                            color = if (selectedItem == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                    }, selected = selectedItem == index, onClick = { selectedItem = index })
                }
            }
        }) {
            Box(Modifier
                .padding(it)
                .background(Color.LightGray)
                .fillMaxSize()) {
                when (selectedItem) {
                    0 -> CreatePage(navController)
                    1 -> ListPage(navController)
                }
            }
        }
    }

}
