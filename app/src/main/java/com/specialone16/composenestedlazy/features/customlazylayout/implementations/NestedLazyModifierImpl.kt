package com.specialone16.composenestedlazy.features.customlazylayout.implementations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.specialone16.composenestedlazy.features.customlazylayout.ComposeNestedLazy

class NestedLazyModifierImpl : ComposeNestedLazy<LazyItemScope, ColumnScope>() {

    private val nestedScrollConnection by lazy {
        object : NestedScrollConnection {

        }
    }

    private val nestedScrollDispatcher by lazy {
        NestedScrollDispatcher()
    }

    @Composable
    override fun ParentLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable LazyItemScope.(group: Int) -> Unit
    ) {
        LazyColumn(
            modifier.nestedScroll(nestedScrollConnection, nestedScrollDispatcher)
        ) {
            items(count) { content(it) }
        }
    }

    @Composable
    override fun LazyItemScope.ChildLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable ColumnScope.(group: Int) -> Unit
    ) {
        Column(modifier) {
            repeat(count) { content(it) }
        }
    }
}
