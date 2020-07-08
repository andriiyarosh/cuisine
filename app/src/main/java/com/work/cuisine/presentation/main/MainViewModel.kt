package com.work.cuisine.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.repository.ReceiptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val receiptsRepository: ReceiptsRepository) :
    ViewModel() {

    private val _receiptInfo: MutableLiveData<List<Receipt>> = MutableLiveData()
    val receiptInfo: LiveData<List<Receipt>> = _receiptInfo

    fun initReceiptInfoList() {
        viewModelScope.launch(Dispatchers.IO) {
            _receiptInfo.postValue(
                receiptsRepository.getSavedReceipts()
            )
        }
    }
}