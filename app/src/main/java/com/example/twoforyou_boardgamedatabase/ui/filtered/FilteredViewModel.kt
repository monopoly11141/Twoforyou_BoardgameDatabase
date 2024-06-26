package com.example.twoforyou_boardgamedatabase.ui.filtered

import androidx.lifecycle.ViewModel
import com.example.twoforyou_boardgamedatabase.domain.FilteredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilteredViewModel @Inject constructor(
    private val repository: FilteredRepository
) : ViewModel() {


}