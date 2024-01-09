package com.specialone16.composenestedlazy.features.comments.impl

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.specialone16.composenestedlazy.features.comments.Komentar
import com.specialone16.composenestedlazy.features.comments.util.ImageWithText

@Composable
fun HybridAndRecycleComment(
    list: List<Komentar>,
    modifier: Modifier = Modifier
) {
    val adapter = remember { RecycleAdapter(list) }
    val adapterList by adapter.list.collectAsState()

    LazyColumn(
        modifier.semantics { contentDescription = "comments" }
    ) {
        itemsIndexed(adapterList.chunked(9)) { index, chunk ->
            chunk.forEach { pesan ->
                when (pesan.tipe) {
                    is TipePesan.Komentar -> Column {
                        ImageWithText(
                            upperText = pesan.nama,
                            lowerText = pesan.pesan,
                            modifier = Modifier.padding(8.dp)
                        )
                        TextButton(onClick = { adapter.toggle(pesan.tipe.komentarId) }) {
                            Text(text = "Replies")
                        }
                    }

                    is TipePesan.BalasanKomentar -> ImageWithText(
                        upperText = pesan.nama,
                        lowerText = pesan.pesan,
                        modifier = Modifier.padding(start = 48.dp, top = 8.dp, bottom = 8.dp)
                    )

                    null -> Unit
                }
            }
        }
    }

    ReportDrawn()
}
