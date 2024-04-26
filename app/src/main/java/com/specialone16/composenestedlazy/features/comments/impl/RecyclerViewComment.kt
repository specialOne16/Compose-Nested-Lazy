package com.specialone16.composenestedlazy.features.comments.impl

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.specialone16.composenestedlazy.features.comments.BalasanKomentar
import com.specialone16.composenestedlazy.features.comments.Komentar
import com.specialone16.composenestedlazy.features.comments.util.ImageWithText
import com.specialone16.composenestedlazy.features.comments.util.Item

@Composable
fun RecyclerViewComment(
    list: List<Komentar>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier, factory = {
        val view = RecyclerView(it)

        fun childAdapter() = object : RecyclerView.Adapter<ViewHolder>() {
            var childList: List<BalasanKomentar> = emptyList()
            var visible by mutableStateOf(true)
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return object : ViewHolder(ComposeView(parent.context)) {
                }
            }

            override fun getItemCount(): Int = childList.size

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val balasanKomentar = childList[position]
                (holder.itemView as? ComposeView)?.apply {
                    setContent {
                        if (visible)
                            ImageWithText(item = Item(balasanKomentar.nama, balasanKomentar.pesan))
                    }
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                    )
                }
            }

        }

        val parentAdapter = object : RecyclerView.Adapter<ViewHolder>() {
            var visibleList = List(list.size) { mutableStateOf(false) }.toMutableList()
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                return object : ViewHolder(LinearLayout(parent.context)) {

                }
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                (holder.itemView as? LinearLayout)?.apply {
                    val komentar = list[position]
                    removeAllViews()
                    addView(ComposeView(context).apply {
                        setContent {
                            Column {
                                ImageWithText(
                                    Item(
                                        komentar.nama,
                                        komentar.pesan
                                    ),
                                    Modifier.padding(start = 24.dp)
                                )
                                Button(onClick = { visibleList[position].value = !visibleList[position].value }) {
                                    Text(text = "Replies")
                                }
                            }
                        }
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                        )
                    })
                    addView(
                        RecyclerView(context).apply {
                            adapter = childAdapter().apply {
                                childList = komentar.balasan
                                visible = visibleList[position].value
                            }
                            layoutManager = LinearLayoutManager(it)
                        }
                    )

                }
            }

            override fun getItemCount(): Int {
                return list.size
            }
        }

        view.adapter = parentAdapter
        view.layoutManager = LinearLayoutManager(it)

        view
    })
}