package com.danielpasser.posts.model

import android.os.Parcel
import android.os.Parcelable

data class CommentsCodable(val comments:List<CommentCodable>)

data class CommentCodable(
    val id: Int=-1,
    val owner: Int=-1,
    val text: String?="",
    val created_at: String?="",
    val updated_at: String?="",
    val module: String?="",
    val module_id: Int=-1
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(owner)
        parcel.writeString(text)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeString(module)
        parcel.writeInt(module_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentCodable> {
        override fun createFromParcel(parcel: Parcel): CommentCodable {
            return CommentCodable(parcel)
        }

        override fun newArray(size: Int): Array<CommentCodable?> {
            return arrayOfNulls(size)
        }
    }
}
