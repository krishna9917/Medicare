package com.application.comeato.Utilities
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.application.comeato.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso


@BindingAdapter("app:netImageSrc","app:placeholder", requireAll = false)
fun loadImage(view:ImageView,url:String?,placeholder:Drawable?)
{
    try
    {
        Picasso.get().load(url).placeholder((placeholder?:view.context.getDrawable(R.drawable.loading))!!).into(view)
    } catch (e: Exception)
    {
        Log.d("ERROR", "loadImage: "+e.message)
    }
}


@SuppressLint("UseCompatTextViewDrawableApis")
@BindingAdapter("drawableTint")
fun setDrawableTint(view: TextView, color: ColorStateList?) {
    if (color != null) {
        view.compoundDrawableTintList = color
    }
}

@BindingAdapter("android:textStyle")
fun setTextViewStyle(textView: TextView, textStyle: Int) {
    textView.setTypeface(null, textStyle)
}

@BindingAdapter("strikeThrough")
fun setStrikeThrough(textView: TextView, strikeThrough: Boolean)
{
    if (strikeThrough)
    {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}


@BindingAdapter("app:setDrawableSize")
fun setDrawableSize(textView: TextView, size: Int) {
    val drawables = textView.compoundDrawables
    for (drawable in drawables) {
        drawable?.setBounds(0, 0, size, size)
    }
    textView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3])
}


@BindingAdapter("app:cardDrawable")
fun cardDrawable(cardView: MaterialCardView,drawable:Drawable)
{
    cardView.background=drawable
}






