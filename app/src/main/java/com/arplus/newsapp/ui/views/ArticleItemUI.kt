package com.arplus.newsapp.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.arplus.newsapp.R
import com.arplus.newsapp.domain.model.Article
import com.arplus.newsapp.ui.theme.Red
import com.arplus.newsapp.utils.asUri
import com.arplus.newsapp.utils.openInBrowser

@Composable
fun ArticleItemUI(article: Article, onFavClick: (Article) -> Unit) {
    val context = LocalContext.current
    Row(Modifier.fillMaxWidth()) {
        var isFavorite by remember {
            mutableStateOf(article.isFavourite)
        }
        val myImg = article.image.ifEmpty { R.drawable.no_image }
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = myImg)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).placeholder(R.drawable.loading).build()
        )
        ConstraintLayout(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        ) {
            val (fav) = createRefs()
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
            )
            Icon(
                painter = painterResource(id = if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
                contentDescription = "fav", tint = Red, modifier = Modifier
                    .constrainAs(fav) {
                        top.linkTo(parent.top, 4.dp)
                        end.linkTo(parent.end, 4.dp)
                    }
                    .clickable(onClick = {
                        isFavorite = !isFavorite
                        onFavClick(article)
                    })
            )
        }

        Column(modifier = Modifier.padding(start = 12.dp)) {

            HtmlText(
                html = article.title,
                maxLines = 1
            )
            HtmlText(
                html = article.description, maxLines = 1
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HtmlText(
                    html = article.author,
                    modifier = Modifier.weight(1f), maxLines = 1
                )
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray
                )
                HtmlText(
                    html = article.publishedAt, maxLines = 1
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        article.url
                            .asUri()
                            ?.openInBrowser(context)
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Read full article", color = Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_right_alt_24),
                        contentDescription = "", tint = Red
                    )

                }

            }
        }


    }
}