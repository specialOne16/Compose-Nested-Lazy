package com.specialone16.composenestedlazy.features.comments.impl

import androidx.activity.compose.ReportDrawn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.specialone16.composenestedlazy.features.comments.Komentar
import com.specialone16.composenestedlazy.features.comments.util.ImageWithText

@Composable
fun NestedComment(
    list: List<Komentar>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "comments"
            }
    ) {
        itemsIndexed(list) { idx, komentar ->
            Column(modifier.padding(8.dp)) {
                var showReply by rememberSaveable { mutableStateOf(false) }

                ImageWithText(
                    upperText = komentar.nama,
                    lowerText = komentar.pesan
                )
                TextButton(
                    onClick = { showReply = !showReply }
                ) { Text(text = "Replies") }
                if (showReply) {
                    komentar.balasan.forEach { balasanKomentar ->
                        ImageWithText(
                            modifier = Modifier.padding(
                                start = 48.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ),
                            upperText = balasanKomentar.nama,
                            lowerText = balasanKomentar.pesan
                        )
                    }
                }
            }
        }
    }

    ReportDrawn()
}
