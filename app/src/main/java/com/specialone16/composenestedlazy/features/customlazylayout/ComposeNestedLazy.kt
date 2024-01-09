package com.specialone16.composenestedlazy.features.customlazylayout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun <ParentScope, ChildScope> ComposeNestedLazy<ParentScope, ChildScope>.NormalCase() {
    ParentLazy(100) { index ->
        Text(
            text = "Greetings $index",
            modifier = Modifier.background(Color.Yellow)
        )
    }
}


@Composable
fun <ParentScope, ChildScope> ComposeNestedLazy<ParentScope, ChildScope>.NestedCase() {
    ParentLazy(100) { parentIndex ->
        val background = Color(Random.nextLong())
        ChildLazy(50) { childIndex ->
            Text(
                text = "$parentIndex.$childIndex Greetings!",
                modifier = Modifier.background(background)
            )
        }
    }
}

abstract class ComposeNestedLazy<ParentScope, ChildScope> {

    @Composable
    abstract fun ParentLazy(
        count: Int, modifier: Modifier, content: @Composable ParentScope.(group: Int) -> Unit
    )

    @Composable
    fun ParentLazy(
        count: Int, content: @Composable ParentScope.(group: Int) -> Unit
    ) = ParentLazy(count, Modifier, content = content)

    @Composable
    abstract fun ParentScope.ChildLazy(
        count: Int, modifier: Modifier, content: @Composable ChildScope.(group: Int) -> Unit
    )

    @Composable
    fun ParentScope.ChildLazy(
        count: Int, content: @Composable ChildScope.(group: Int) -> Unit
    ) = ChildLazy(count, Modifier, content = content)

    private var count = 0
    protected fun childItemComposed() {
        count++
        Log.i("ComposeNestedLazy", "Child item composed $count times")
    }
}
