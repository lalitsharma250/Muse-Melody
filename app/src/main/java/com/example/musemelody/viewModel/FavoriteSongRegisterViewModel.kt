package com.example.musemelody.viewModel

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.musemelody.register.FavoriteSongRegister

class FavoriteSongRegisterViewModel() : ViewModel(), FavoriteSongRegister, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteSongRegisterViewModel> {
        override fun createFromParcel(parcel: Parcel): FavoriteSongRegisterViewModel {
            return FavoriteSongRegisterViewModel(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteSongRegisterViewModel?> {
            return arrayOfNulls(size)
        }
    }
}