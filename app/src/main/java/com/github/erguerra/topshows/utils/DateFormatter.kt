package com.github.erguerra.topshows.utils

fun formatDateToBrazilian(date: String?) :  String{
    val splittedDate = date?.split("-")
    val year = splittedDate?.get(0)
    val month = splittedDate?.get(1)
    val day = splittedDate?.get(2)

    return "$day/$month/$year"
}