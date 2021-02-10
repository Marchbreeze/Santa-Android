package org.sopt.santamanitto.room.create

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R
import org.sopt.santamanitto.room.create.data.ExpirationLiveData

@BindingAdapter("expirationDiff")
fun setExpirationDiff(view: AppCompatTextView, expiration: ExpirationLiveData) {
    view.text = String.format(view.context.getText(R.string.createroom_preview_start).toString(), expiration.dayDiff)
}

@BindingAdapter("expirationPreview")
fun setExpirationPreview(view: AppCompatTextView, expiration: ExpirationLiveData) {
    val context = view.context
    val amPmStr = if (expiration.isAm) {
        context.getString(R.string.createroom_am)
    } else {
        context.getString(R.string.createroom_pm)
    }
    view.text =
            String.format(context.getText(R.string.createroom_preview).toString(),
                    expiration.year, expiration.month, expiration.day,
                    amPmStr, expiration.hour, expiration.minute)
}

@BindingAdapter("expirationTime")
fun setExpirationTime(view: AppCompatTextView, expiration: ExpirationLiveData) {
    view.text = String.format(view.context.getText(R.string.createroom_expiration_time).toString(),
                    expiration.hour, expiration.minute)
}