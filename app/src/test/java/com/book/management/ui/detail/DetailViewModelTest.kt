package com.book.management.ui.detail

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
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        viewModel = spyk()
    }

    @Test
    fun `when Update call http updateBook`() {

        //Given
        val bookBean = BookBean(1, "name", "author", 2024, "isbn")
        every { viewModel.updateBook(bookBean) }.just(Runs)

        // When
        viewModel.dispatch(DetailAction.Update(bookBean))

        // Then
        verify {
            viewModel.updateBook(bookBean)
        }
    }

    @Test
    fun `when Delete call http deleteBook`() {

        //Given
        val bookId = 1L
        every { viewModel.deleteBook(bookId) }.just(Runs)

        // When
        viewModel.dispatch(DetailAction.Delete(bookId))

        // Then
        verify {
            viewModel.deleteBook(bookId)
        }
    }
}