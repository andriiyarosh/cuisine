package com.work.cuisine.presentation.receipts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.work.cuisine.network.moodels.receipt.Receipt

open class BaseReceiptViewModel: ViewModel() {

    protected val _receipts: MutableLiveData<RequestSteps> = MutableLiveData()
    val receipts: LiveData<RequestSteps> = _receipts

    sealed class RequestSteps {
        object StartRequest : RequestSteps()
        class RequestFinished(val receipts: List<Receipt>): RequestSteps()
    }
}