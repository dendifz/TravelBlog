package com.latihan.travelblogindonesia.http

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Blog protected constructor(`in`: Parcel) : Parcelable {
    val id: String?
    var author: Author?
    val title: String?
    val date: String?
    val image: String?
    val description: String?
    val views: Int
    val rating: Float


    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(title)
        dest.writeString(date)
        dest.writeString(image)
        dest.writeString(description)
        dest.writeInt(views)
        dest.writeFloat(rating)
        dest.writeParcelable(author, 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    val imageURL: String
        get() = BlogHttpClient.BASE_URL + BlogHttpClient.PATH + image

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val blog = o as Blog
        return views == blog.views && java.lang.Float.compare(blog.rating, rating) == 0 &&
                id == blog.id &&
                author == blog.author &&
                title == blog.title &&
                date == blog.date &&
                image == blog.image &&
                description == blog.description
    }

    override fun hashCode(): Int {
        return Objects.hash(id, author, title, date, image, description, views, rating)
    }

    companion object {
        @JvmField val CREATOR: Creator<Blog> = object : Creator<Blog> {
            override fun createFromParcel(`in`: Parcel): Blog? {
                return Blog(`in`)
            }

            override fun newArray(size: Int): Array<Blog?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        id = `in`.readString()
        title = `in`.readString()
        date = `in`.readString()
        image = `in`.readString()
        description = `in`.readString()
        views = `in`.readInt()
        rating = `in`.readFloat()
        author = `in`.readParcelable(Author::class.java.classLoader)
    }
}