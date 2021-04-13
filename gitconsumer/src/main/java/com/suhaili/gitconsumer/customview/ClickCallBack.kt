package com.suhaili.gitconsumer.customview

import com.suhaili.gitconsumer.model.FavoritModel

interface ClickCallBack {
    fun ItemClicked( fav : FavoritModel)
}