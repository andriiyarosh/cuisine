package com.work.cuisine.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.work.cuisine.R
import com.work.cuisine.network.moodels.receipt.Cuisines
import kotlinx.android.synthetic.main.view_filter.view.*

class FilterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var state: State = State.EXPANDED
    private var listener: () -> Unit = {}
    private var isCuisineEnabled: Boolean = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        initButtons()
        initSpinner()
    }

    fun expand() = updateState(State.EXPANDED)

    fun getNumber() =
        if (number.text.toString().isNotEmpty()) number.text.toString().toInt()
        else 100

    fun enableCuisinePicker(isEnabled: Boolean) {
        this.isCuisineEnabled = isEnabled
    }

    fun getQuery() = query.text.toString()
    fun getCuisine() =
        Cuisines.valueOf(cuisine.getItemAtPosition(cuisine.selectedItemPosition).toString())

    fun onFindClickedListener(listener: () -> Unit) {
        this.listener = listener
    }

    private fun initSpinner() {
        val arrayAdapter = ArrayAdapter(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            Cuisines.values().map { it.name }
        )
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        cuisine.adapter = arrayAdapter
    }

    private fun initButtons() {
        cancel.setOnClickListener {
            TransitionManager.beginDelayedTransition(this)
            searchQuery.isVisible = false
            numberTv.isVisible = false
            number.isVisible = false
            cuisine.isVisible = false
            cancel.isVisible = false
            find.text = context.getString(R.string.filter)
            find.setOnClickListener { updateState(State.EXPANDED) }
            state = State.HIDDEN
        }
        updateState(State.HIDDEN)
    }

    private fun updateState(state: State) {
        if (this.state == state) return
        when (state) {
            State.HIDDEN -> hideView()
            State.EXPANDED -> showView()
        }
        this.state = state
    }

    private fun hideView() {
        searchQuery.isVisible = false
        numberTv.isVisible = false
        number.isVisible = false
        cuisine.isVisible = false
        cancel.isVisible = false
        find.text = context.getString(R.string.filter)
        find.setOnClickListener { updateState(State.EXPANDED) }
        state = State.HIDDEN
    }

    private fun showView() {
        searchQuery.isVisible = true
        numberTv.isVisible = true
        number.isVisible = true
        cuisine.isVisible = isCuisineEnabled
        cancel.isVisible = true
        find.text = context.getString(R.string.find)
        find.setOnClickListener {
            updateState(State.HIDDEN)
            listener.invoke()
        }
        state = State.EXPANDED
    }

    enum class State {
        HIDDEN,
        EXPANDED
    }
}