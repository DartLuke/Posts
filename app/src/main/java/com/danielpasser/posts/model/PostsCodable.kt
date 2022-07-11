package com.danielpasser.posts.model

import android.os.Parcel
import android.os.Parcelable

data class PostsCodable(val posts: List<PostCodable>)



data class PostCodable(
    val id: Int=-1,
    val owner: Int=-1, // User.id (publisher)
    val text: String?="",
    val created_at: String?="", // (datetime)
    val updated_at: String?="" // (datetime)
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(owner)
        parcel.writeString(text)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostCodable> {
        override fun createFromParcel(parcel: Parcel): PostCodable {
            return PostCodable(parcel)
        }

        override fun newArray(size: Int): Array<PostCodable?> {
            return arrayOfNulls(size)
        }
    }
}
