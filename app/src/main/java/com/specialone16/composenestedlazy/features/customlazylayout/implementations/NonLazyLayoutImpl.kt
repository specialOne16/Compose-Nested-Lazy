package com.specialone16.composenestedlazy.features.customlazylayout.implementations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.specialone16.composenestedlazy.features.customlazylayout.ComposeNestedLazy

class NonLazyLayoutImpl : ComposeNestedLazy<ColumnScope, ColumnScope>() {

    @Composable
    override fun ParentLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable ColumnScope.(group: Int) -> Unit
    ) {
        Column(modifier.verticalScroll(rememberScrollState())) {
            repeat(count) { content(it) }
        }
    }

    @Composable
    override fun ColumnScope.ChildLazy(
        count: Int,
        modifier: Modifier,
        content: @Composable() (ColumnScope.(group: Int) -> Unit)
    ) {
        Column(modifier) {
            repeat(count) {
                content(it)
                childItemComposed()
            }
        }
    }
}
