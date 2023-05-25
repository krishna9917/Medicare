package com.application.comeato.Utilities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.application.comeato.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.squareup.picasso.Picasso


@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("app:netImageSrc","app:placeholder", requireAll = false)
fun loadImage(view:ImageView,url:String?,placeholder:Drawable?)
{
    try
    {
        Picasso.get().load(url).resize(800,800).onlyScaleDown().placeholder((placeholder?:view.context.getDrawable(R.drawable.loading))!!).error(R.drawable.logo).into(view)
    } catch (e: Exception)
    {
        view.setImageDrawable(placeholder?:view.context.getDrawable(R.drawable.logo))
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


@BindingAdapter("app:showProgress")
fun showProgressOnMaterialButton(button:MaterialButton,isVisible:Boolean)
{
    val spec = CircularProgressIndicatorSpec(button.context,null, 0, com.google.android.material.R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall)
    spec.indicatorColors= intArrayOf(Color.RED, Color.GREEN, Color.YELLOW)
    val progressIndicatorDrawable = IndeterminateDrawable.createCircularDrawable(button.context, spec)
    if(isVisible)
    {
        button.icon = progressIndicatorDrawable
        button.isEnabled=false
        button.isClickable=false
        button.setTextColor(Color.GRAY)
    }else
    {
        button.icon = null
        button.isEnabled=true
        button.isClickable=true
        button.setTextColor(Color.WHITE)
    }
}

@BindingAdapter("app:isWishListed")
fun showWishListed(image:ImageView,isWishListed:Boolean)
{
    if(isWishListed)
    {
        image.setImageDrawable(image.context.getDrawable(R.drawable.ic_liked))
    }else
    {
        image.setImageDrawable(image.context.getDrawable(R.drawable.ic_not_liked))
    }
    UtilsFunction.setAnimation(image.context,image,R.anim.animation_bounce)
}


@BindingAdapter("app:animatedVisibility")
fun showVisibility(view:View,isVisible: Boolean)
{
    if(isVisible)
    {
        view.visibility=View.VISIBLE
        UtilsFunction.setAnimation(view.context,view,R.anim.animation_slide_up)
    }else
    {
        view.visibility=View.GONE
        UtilsFunction.setAnimation(view.context,view,R.anim.animation_slide_down)
    }

}












