package com.djabaridev.anicatalog.presentation.features.search

import androidx.lifecycle.ViewModel
import com.djabaridev.anicatalog.domain.repositories.AniCatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: AniCatalogRepository,
) : ViewModel() {
}