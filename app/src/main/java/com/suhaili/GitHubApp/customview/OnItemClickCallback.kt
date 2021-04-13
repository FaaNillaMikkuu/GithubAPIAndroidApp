package com.suhaili.GitHubApp.customview

import com.suhaili.GitHubApp.model.GitModel

interface OnItemClickCallback {
    fun onItemClicked(model: GitModel)
}