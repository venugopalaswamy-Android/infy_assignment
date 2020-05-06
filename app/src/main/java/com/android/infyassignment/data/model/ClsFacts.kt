package com.android.infyassignment.data.model

data class ClsFacts(
    val description: String,
    val imageHref: String,
    val title: String
){
    override fun toString(): String {
        return "ClsFacts(description='$description', imageHref='$imageHref', title='$title')"
    }
}