package com.work.cuisine.presentation.receipts.random

import androidx.lifecycle.viewModelScope
import com.work.cuisine.presentation.receipts.BaseReceiptViewModel
import com.work.cuisine.repository.ReceiptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomReceiptViewModel @Inject constructor(private val receiptsRepository: ReceiptsRepository) : BaseReceiptViewModel() {

    fun findRandomReceipts() {
        _receipts.value = RequestSteps.StartRequest
        viewModelScope.launch(Dispatchers.IO) {
            _receipts.postValue(
                RequestSteps.RequestFinished(
                    receiptsRepository.getRandomReceipt()
                )
            )
        }
    }
}