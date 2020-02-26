package com.github.erguerra.topshows.utils

fun formatDateToBrazilian(date: String?) :  String{
    val separatedDate = date?.split("-")
    val year = separatedDate?.get(0)
    val month = separatedDate?.get(1)
    val day = separatedDate?.get(2)

    return "$day/$month/$year"
}
