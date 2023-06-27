package com.example.musemelody.viewModel

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.musemelody.register.ShortcutRegister

class ShortcutViewModel() : ViewModel(), ShortcutRegister, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShortcutViewModel> {
        override fun createFromParcel(parcel: Parcel): ShortcutViewModel {
            return ShortcutViewModel(parcel)
        }

        override fun newArray(size: Int): Array<ShortcutViewModel?> {
            return arrayOfNulls(size)
        }
    }
}