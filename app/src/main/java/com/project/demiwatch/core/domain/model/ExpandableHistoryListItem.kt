package com.project.demiwatch.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpandableHistoryListItem(
    var historyListItem: HistoryItem,
    var isExpandable: Boolean,
) : Parcelable