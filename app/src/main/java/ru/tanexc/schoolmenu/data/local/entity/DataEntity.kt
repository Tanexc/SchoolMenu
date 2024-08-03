package ru.tanexc.schoolmenu.data.local.entity

import ru.tanexc.schoolmenu.domain.model.Domain

interface DataEntity {
    fun asDomain(): Domain
}