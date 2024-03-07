package com.specialone16.composenestedlazy.features.comments

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.specialone16.composenestedlazy.features.comments.impl.FlattenComment
import com.specialone16.composenestedlazy.features.comments.impl.GoneReplyComment
import com.specialone16.composenestedlazy.features.comments.impl.HybridAndRecycleComment
import com.specialone16.composenestedlazy.features.comments.impl.PartialFlattenComment
import com.specialone16.composenestedlazy.features.comments.impl.NestedComment
import com.specialone16.composenestedlazy.features.comments.impl.RecycleComment


data class Komentar(
    val nama: String,
    val pesan: String,
    val balasan: List<BalasanKomentar>
)

data class BalasanKomentar(
    val nama: String,
    val pesan: String
)


sealed interface CommentImplementationAttr {
    data class Nested(val items: List<Komentar>) : CommentImplementationAttr
    data class Flatten(val items: List<Komentar>) : CommentImplementationAttr
    data class PartialFlattenComment(val items: List<Komentar>) : CommentImplementationAttr
    data class Recycle(val items: List<Komentar>): CommentImplementationAttr
    data class HybridRecycle(val items: List<Komentar>): CommentImplementationAttr
    data class GoneReply(val items: List<Komentar>): CommentImplementationAttr
}

@Composable
fun CommentImplementation(
    attr: CommentImplementationAttr,
    modifier: Modifier = Modifier
) {
    when (attr) {
        // Solusi stackoverflow
        is CommentImplementationAttr.Flatten -> FlattenComment(
            list = attr.items,
            modifier = modifier
        )
        // Implementasi awal
        is CommentImplementationAttr.Nested -> NestedComment(
            list = attr.items,
            modifier = modifier
        )
        // Alternatif solusi 1
        is CommentImplementationAttr.PartialFlattenComment -> PartialFlattenComment(
            list = attr.items,
            modifier = modifier
        )
        // Alternatif solusi 2
        is CommentImplementationAttr.Recycle -> RecycleComment(
            list = attr.items,
            modifier = modifier
        )
        // Alternatif solusi 4
        is CommentImplementationAttr.HybridRecycle -> HybridAndRecycleComment(
            list = attr.items,
            modifier = modifier
        )
        // Alternatif solusi 3
        is CommentImplementationAttr.GoneReply -> GoneReplyComment(
            list = attr.items,
            modifier = modifier
        )
    }
}
