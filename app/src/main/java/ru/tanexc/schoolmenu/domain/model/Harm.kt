package ru.tanexc.schoolmenu.domain.model

/**
 * enum, содержащий уровень полезности/вредности еды
 */
enum class Harm(val harm: String) {
    Danger("Опасно"), Normal("Нормально"), Helthy("Полезно"), NotSpecified("Неопределено")
}