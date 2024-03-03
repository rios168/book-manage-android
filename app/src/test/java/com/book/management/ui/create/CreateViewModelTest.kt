package com.book.management.ui.create

import com.book.management.bean.BookBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CreateViewModelTest {

    private lateinit var viewModel: CreateViewModel

    @Before
    fun setUp() {
        viewModel = spyk()
    }

    @Test
    fun `when CreateBook call http createBook`() {
        //Given
        val bookBean = BookBean(1, "name", "author", 2024, "isbn")
        every { viewModel.createBook(bookBean) }.just(Runs)

        // When
        viewModel.dispatch(CreateAction.CreateBook(bookBean))

        // Then
        verify {
            viewModel.createBook(bookBean)
        }
    }
}
