package com.work.cuisine.presentation.receipts.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.cuisine.network.moodels.instructions.Instruction
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.repository.ReceiptsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReceiptInfoViewModel @Inject constructor(private val receiptsRepository: ReceiptsRepository) :
    ViewModel() {

    private val _receiptInfo: MutableLiveData<InitializingSteps> = MutableLiveData()
    val receiptInfo: LiveData<InitializingSteps> = _receiptInfo

    fun getReceiptInfo(receiptId: Long) {
        _receiptInfo.value = InitializingSteps.StartLoading
        viewModelScope.launch(Dispatchers.IO) {
            _receiptInfo.postValue(
                InitializingSteps.FinishedLoading(receiptsRepository.getReceiptInfo(receiptId))
            )
        }
    }

    fun saveReceipt() {
        viewModelScope.launch(Dispatchers.IO) {
            val loadingState = _receiptInfo.value
            loadingState?.let { loadingState ->
                when(loadingState) {
                    InitializingSteps.StartLoading -> return@let
                    is InitializingSteps.FinishedLoading ->{
                        val receipt = loadingState.receiptInfo.first
                        val instruction = loadingState.receiptInfo.second
                        if (receipt != null)
                            receiptsRepository.saveReceipt(receipt, instruction?: Instruction(steps = emptyList()))
                    }
                }

            }
        }
    }

    fun deleteReceipt() {
        viewModelScope.launch(Dispatchers.IO) {
            val receiptInfo = _receiptInfo.value
            receiptInfo?.let {
                when (it) {
                    InitializingSteps.StartLoading -> return@let
                    is InitializingSteps.FinishedLoading -> it.receiptInfo.first?.let { receipt ->
                        receiptsRepository.deleteReceipt(receipt.id)
                    }
                }
            }
        }
    }

    sealed class InitializingSteps {
        object StartLoading : InitializingSteps()
        class FinishedLoading(val receiptInfo: Pair<Receipt?, Instruction?>) : InitializingSteps()
    }
}