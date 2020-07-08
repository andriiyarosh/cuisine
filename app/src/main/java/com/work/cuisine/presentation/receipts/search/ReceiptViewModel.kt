package com.work.cuisine.presentation.receipts.search

import androidx.lifecycle.viewModelScope
import com.work.cuisine.network.moodels.receipt.Cuisines
import com.work.cuisine.presentation.receipts.BaseReceiptViewModel
import com.work.cuisine.repository.ReceiptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReceiptViewModel @Inject constructor(private val receiptsRepository: ReceiptsRepository) :BaseReceiptViewModel() {

    fun findReceipts(query: String, number: Int = 100, cuisine: Cuisines = Cuisines.Any) {
        _receipts.postValue(RequestSteps.StartRequest)
        viewModelScope.launch(Dispatchers.IO) {
            _receipts.postValue(
                RequestSteps.RequestFinished(
                    receiptsRepository.getReceipt(query, cuisine.value, number)
                        ?.apply {
                            val uri = baseUri
                            receipts.forEach { it.image = uri + it.image }
                        }?.receipts
                        ?: emptyList()
                )
            )
        }
    }
}