package com.specialone16.composenestedlazy.features.comments.impl

import androidx.activity.compose.ReportDrawn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun PartialFlattenComment(
    list: List<Komentar>,
    modifier: Modifier = Modifier
) {
    val replyPerChunk = 10
    var showReply by remember {
        mutableStateOf(List(list.size) { false })
    }

    LazyColumn(
        modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "comments"
            }
    ) {
        list.forEachIndexed { idx, komentar ->
            item {
                ImageWithText(
                    upperText = komentar.nama,
                    lowerText = komentar.pesan
                )
                TextButton(
                    onClick = {
                        showReply = showReply
                            .mapIndexed { index, b -> if (index == idx) !b else b }
                    }
                ) { Text(text = "Replies") }
            }

            komentar.balasan.chunked(replyPerChunk).forEach { balasanKomentarChunk ->
                if (showReply[idx]) {
                    item {
                        balasanKomentarChunk.forEach { balasanKomentar ->
                            ImageWithText(
                                upperText = balasanKomentar.nama,
                                lowerText = balasanKomentar.pesan,
                                modifier = Modifier.padding(
                                    start = 48.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    ReportDrawn()
}


