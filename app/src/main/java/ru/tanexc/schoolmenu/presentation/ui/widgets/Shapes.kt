package ru.tanexc.schoolmenu.presentation.ui.widgets

import androidx.compose.foundation.shape.RoundedCornerShape


fun topStartShape(
    rowCount: Int,
    columnCount: Int
): RoundedCornerShape =
    RoundedCornerShape(
        50,
        if (columnCount > 1) 0 else 50,
        if (rowCount > 1 || columnCount > 1) 0 else 50,
        if (rowCount > 1) 0 else 50
    )

fun topEndShape(
    rowCount: Int,
    columnCount: Int
): RoundedCornerShape =
    RoundedCornerShape(
        if (columnCount > 1) 0 else 50,
        50,
        if (rowCount > 1) 0 else 50,
        if (rowCount > 1 || columnCount > 1) 0 else 50
    )

fun middleShape(): RoundedCornerShape = RoundedCornerShape(0)

fun bottomStartShape(
    rowCount: Int,
    columnCount: Int
): RoundedCornerShape =
    RoundedCornerShape(
        if (rowCount > 1) 0 else 50,
        if (rowCount > 1) 0 else 50,
        if (columnCount > 1) 0 else 50,
        50
    )

fun bottomEndShape(
    rowCount: Int,
    columnCount: Int
): RoundedCornerShape =
    RoundedCornerShape(
        if (rowCount > 1) 0 else 50,
        if (rowCount > 1) 0 else 50,
        50,
        if (columnCount > 1) 0 else 50
    )

