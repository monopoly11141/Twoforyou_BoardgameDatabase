package com.example.twoforyou_boardgamedatabase.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twoforyou_boardgamedatabase.domain.DetailRepository
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayUiState
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.xml.sax.XMLReader
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = combine(
        repository.htmlString,
        _state
    ) { array ->
        DetailUiState(
            htmlString = array[0] as String
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun updateHtmlString() {
        viewModelScope.launch {
            repository.updateHtmlString()
        }
        _state.update {
            it.copy(
                htmlString = repository.htmlString.value
            )
        }
    }

    fun xmlToListParser(xmlString: String) : String {
        val xmlMapper = XmlMapper()
        val stringList = mutableListOf<String>()
        try {
            stringList.add(xmlMapper.readValue(xmlString, stringList.javaClass).toString())

        }catch(e: Exception) {
            e.printStackTrace()
        }
        return stringList.joinToString()
    }
}