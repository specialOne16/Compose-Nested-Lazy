package com.specialone16.composenestedlazy.features.customlazylayout.implementations

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.specialone16.composenestedlazy.features.customlazylayout.ComposeNestedLazy

class FillParentImpl : ComposeNestedLazy<LazyItemScope, Any>() {

    @Composable
    override fun ParentLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable LazyItemScope.(group: Int) -> Unit
    ) {
        LazyColumn(modifier) {
            items(100) { content(it) }
        }
    }

    @Composable
    override fun LazyItemScope.ChildLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable Any.(group: Int) -> Unit
    ) {
        LazyColumn(modifier.fillParentMaxHeight()) {
            items(100) {
                content(it)
                childItemComposed()
            }
        }
    }
}