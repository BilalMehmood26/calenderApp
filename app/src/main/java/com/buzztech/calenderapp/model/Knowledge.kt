package com.buzztech.calenderapp.model

data class Knowledge(
    val KnowledgeFor: String = "",
    val content: List<String> = ArrayList(),
    val header: ArrayList<String> = ArrayList(),
    val type: List<String> = ArrayList(),
    val data: HashMap<String, String> = hashMapOf(),
    val images: HashMap<String, List<String>> = hashMapOf(),
)
