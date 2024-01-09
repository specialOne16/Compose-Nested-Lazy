package com.specialone16.composenestedlazy.features.comments.impl

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
fun GoneReplyComment(
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
            }
        }
    }

    LazyColumn(modifier.semantics {
        contentDescription = "comments"
    }) {
        flattenList.forEachIndexed { idx, pesan ->
            when (pesan.tipe) {
                is TipePesan.Komentar -> {
                    item {
                        ImageWithText(
                            upperText = pesan.nama,
                            lowerText = pesan.pesan
                        )

                        TextButton(
                            onClick = {
                                showReply = showReply.mapIndexed { idx, value ->
                                    if (idx == pesan.tipe.komentarId) !value else value
                                }
                            }
                        ) { Text(text = "Replies") }
                    }
                }

                is TipePesan.BalasanKomentar -> {
                    if (showReply[pesan.tipe.komentarId]) {
                        item {
                            ImageWithText(
                                modifier = Modifier.padding(
                                    start = 48.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ),
                                upperText = pesan.nama,
                                lowerText = pesan.pesan
                            )
                        }
                    }
                }

                null -> Unit
            }
        }
    }

    ReportDrawn()
}
