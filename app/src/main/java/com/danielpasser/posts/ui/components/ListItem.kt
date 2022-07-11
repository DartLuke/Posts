package com.danielpasser.posts.ui.components

import android.app.LauncherActivity
import android.icu.text.CaseMap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListItem(
    title:String,
    text:String,
    onClick :()  -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 6.dp,
                end = 6.dp,
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .border(BorderStroke(width = 2.dp, color = Color.Gray))
            .clickable(onClick = onClick),
        elevation = 8.dp,
    )

    {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentWidth(Alignment.Start)
                    .heightIn(0.dp, 232.dp),
             //   style = MaterialTheme.typography.h5
            )
            Row() {
                ClickableText(
                    text = AnnotatedString("Edit"),
                    onClick = { onClickEdit.invoke() },
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                )
                Spacer(Modifier.width(6.dp))
                ClickableText(
                    text = AnnotatedString("Delete"),
                    onClick = { onClickDelete.invoke() },
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                )
            }
        }
    }
}