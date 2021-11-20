package com.latihan.travelblogindonesia.http

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.util.*

class Author protected constructor(`in`: Parcel) : Parcelable {
    val name: String? = `in`.readString()
    val avatar: String? = `in`.readString()

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    val avatarURL: String
        get() = BlogHttpClient.BASE_URL + BlogHttpClient.PATH + avatar

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val author = o as Author
        return name == author.name &&
                avatar == author.avatar
    }

    override fun hashCode(): Int {
        return Objects.hash(name, avatar)
    }

    companion object {
        @JvmField val CREATOR: Creator<Author> = object : Creator<Author> {
            override fun createFromParcel(`in`: Parcel): Author? {
                return Author(`in`)
            }

            override fun newArray(size: Int): Array<Author?> {
                return arrayOfNulls(size)
            }
        }
    }

}