package com.tbi.moviecompose.util

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

inline fun <T: Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T)->Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) = items(
    count = items.itemCount,
    key = if (key != null){ index ->
        val item = items.peek(index)
        if (item == null)
            PagingPlaceholderKey(index)
        else
            key(item)
    } else null,
    span = {index ->
        val item = items.peek(index)
        when{
            item != null && span != null -> span(item)
            else -> GridItemSpan(0)
        }
    },
    contentType = {index -> items.peek(index)?.let { contentType(it) } }
){
    index -> itemContent(items[index])
}


@SuppressLint("BanParcelableUsage")
data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) = parcel.writeInt(index)
    override fun describeContents(): Int = 0

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}

@Composable
fun PaddingValues.copy(
    copyStart: Boolean = true,
    copyTop: Boolean = true,
    copyEnd: Boolean = true,
    copyBottom: Boolean = true,
): PaddingValues {
    return remember(this) {
        derivedStateOf {
            PaddingValues(
                start = if (copyStart) calculateStartPadding(LayoutDirection.Ltr) else 0.dp,
                top = if (copyTop) calculateTopPadding() else 0.dp,
                end = if (copyEnd) calculateEndPadding(LayoutDirection.Ltr) else 0.dp,
                bottom = if (copyBottom) calculateBottomPadding() else 0.dp,
            )
        }
    }.value
}

