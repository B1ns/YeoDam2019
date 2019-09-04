package com.yeodam.yeodam2019

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.yeodam.yeodam2019.data.Story

class EmployeeDiffCallback(
    private val mOldEmployeeList: ArrayList<Story>,
    private val mNewEmployeeList: ArrayList<Story>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].index === mNewEmployeeList[newItemPosition].index
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldEmployeeList[oldItemPosition]
        val newEmployee = mNewEmployeeList[newItemPosition]

        return oldEmployee.title.equals(newEmployee.title)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}