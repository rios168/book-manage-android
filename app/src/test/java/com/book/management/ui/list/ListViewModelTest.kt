package com.book.management.ui.list

import com.book.management.bean.BookBean
import com.book.management.ui.create.CreateAction
import com.book.management.ui.create.CreateViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Mark on 2024/3/1 001.
 */
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    @Before
    fun setUp() {
        viewModel = spyk()
    }

    @Test
    fun `when UpdateList call http getBookList`() {

        //Given
        every { viewModel.getBookList() }.just(Runs)

        // When
        viewModel.dispatch(ListAction.UpdateList)

        // Then
        verify {
            viewModel.getBookList()
        }
    }
}