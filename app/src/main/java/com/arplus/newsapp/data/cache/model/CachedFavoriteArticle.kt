package com.arplus.newsapp.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arplus.newsapp.domain.model.Article


@Entity(tableName = "favorites")
data class CachedFavoriteArticle(

    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "publishedAt") var publishedAt: String = "",
    @ColumnInfo(name = "title") var title: String = "",
    @PrimaryKey @ColumnInfo(name = "url") var url: String = "",
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = true,
) {
    companion object {
        fun fromDomain(article: Article): CachedFavoriteArticle {
            return CachedFavoriteArticle(
                url = article.url,
                title = article.title,
                publishedAt = article.publishedAt,
                description = article.description,
                content = article.content,
                author = article.author,
                image = article.image,
                isFavorite = true
            )
        }

    }
}

fun CachedFavoriteArticle.toDomain(): Article {

    return Article(
        author, content, description, publishedAt, title, url, image, true
    )
}
