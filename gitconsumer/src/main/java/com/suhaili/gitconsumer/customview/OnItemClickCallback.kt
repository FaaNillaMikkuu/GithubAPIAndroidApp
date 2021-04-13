package com.suhaili.GitHubApp.customview

import com.suhaili.gitconsumer.model.GitModel


interface OnItemClickCallback {
    fun onItemClicked(model: GitModel)
}