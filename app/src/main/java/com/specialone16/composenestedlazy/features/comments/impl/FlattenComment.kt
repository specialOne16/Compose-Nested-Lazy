package com.specialone16.composenestedlazy.features.comments.impl

import androidx.activity.compose.ReportDrawn
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.specialone16.composenestedlazy.features.comments.Komentar
import com.specialone16.composenestedlazy.features.comments.util.ImageWithText

@Composable
fun FlattenComment(
    list: List<Komentar>,
    modifier: Modifier = Modifier
) {
    var showReply by remember { mutableStateOf(List(list.size) { false }) }
    val flattenList = buildList {
        list.forEachIndexed { komentarId, komentar ->
            add(
                Pesan(
                    komentar.nama,
                    komentar.pesan,
                    TipePesan.Komentar(komentarId)
                )
            )
            komentar.balasan.forEach { balasanKomentar ->
                add(
                    Pesan(
                        balasanKomentar.nama,
                        balasanKomentar.pesan,
                        TipePesan.BalasanKomentar(komentarId)
                    )
                )
//                repeat(4) {
//                    add(Pesan("", "", null))
//                }
            }
        }
    }

    LazyColumn(modifier.semantics {
        contentDescription = "comments"
    }) {
        itemsIndexed(flattenList) { idx, pesan ->
            when (pesan.tipe) {
                is TipePesan.Komentar -> {
                    ImageWithText(
                        upperText = pesan.nama,
                        lowerText = pesan.pesan
                    )
                }

                is TipePesan.BalasanKomentar -> {
                    if (showReply[pesan.tipe.komentarId]) {
                        ImageWithText(
                            modifier = Modifier.padding(start = 48.dp, top = 8.dp, bottom = 8.dp),
                            upperText = pesan.nama,
                            lowerText = pesan.pesan
                        )
                    }
                }
                null -> Unit
            }

            if (pesan.tipe is TipePesan.Komentar) {
                TextButton(
                    onClick = {
                        showReply = showReply.mapIndexed { idx, value ->
                            if (idx == pesan.tipe.komentarId) !value else value
                        }
                    }
                ) { Text(text = "Replies") }
            }
        }
    }

    ReportDrawn()
}

sealed interface TipePesan {
    data class Komentar(val komentarId: Int) : TipePesan
    data class BalasanKomentar(val komentarId: Int) : TipePesan
}

data class Pesan(
    val nama: String,
    val pesan: String,
    val tipe: TipePesan?
)