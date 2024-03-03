package com.book.management.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun Int.res() = stringResource(id = this)