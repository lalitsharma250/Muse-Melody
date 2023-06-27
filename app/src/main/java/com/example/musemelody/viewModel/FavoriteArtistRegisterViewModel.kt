package com.example.musemelody.viewModel

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.musemelody.register.FavoriteArtistRegister

class FavoriteArtistRegisterViewModel() : ViewModel(), FavoriteArtistRegister, Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteArtistRegisterViewModel> {
        override fun createFromParcel(parcel: Parcel): FavoriteArtistRegisterViewModel {
            return FavoriteArtistRegisterViewModel(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteArtistRegisterViewModel?> {
            return arrayOfNulls(size)
        }
    }
}