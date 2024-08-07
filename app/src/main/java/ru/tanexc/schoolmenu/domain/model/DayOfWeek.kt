package ru.tanexc.schoolmenu.domain.model

/**
 * enum, содержащий все дни недели и дополнительное значение
 */
enum class DayOfWeek(val text: String, val full: String) {
    Monday("ПН", "Понедельник"),
    Tuesday("ВТ", "Вторник"),
    Wednesday("СР", "Среда"),
    Thursday("ЧТ", "Четверг"),
    Friday("ПТ", "Пятница"),
    Saturday("СБ", "Суббота"),
    Sunday("ВС", "Воскресенье"),
    Any("ВСЕ", "Все")
}