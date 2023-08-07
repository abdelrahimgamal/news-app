package com.arplus.newsapp.data.model

import com.arplus.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

data class ApiArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    @SerializedName("urlToImage") val image: String?
)


fun ApiArticle.mapToDomain(): Article {
    return Article(
        author = author.orEmpty(),
        content = content.orEmpty(),
        image = image.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
    )
}