package ru.tanexc.schoolmenu.domain.model

import ru.tanexc.schoolmenu.data.local.entity.DataEntity

interface Domain {
    fun asEntity(): DataEntity
}