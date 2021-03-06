package sample.kotlin.project.domain.stores.search.pojo

import sample.kotlin.project.domain.core.mvi.pojo.Action
import sample.kotlin.project.domain.pojo.search.Repository
import sample.kotlin.project.domain.repositories.search.SearchRequest

sealed class SearchAction : Action {

    data class OnSearchClick(val query: String) : SearchAction()

    object OnScrolledToBottom : SearchAction()

    object OnRefresh : SearchAction()

    object OnRetryClick : SearchAction()

    object OnRetryNextPageClick : SearchAction()

    data class OnSearchQueryChanged(val query: String) : SearchAction()

    object OnLoadSuggestions : SearchAction()

    data class ConnectivityChanged(val connected: Boolean) : SearchAction()

    data class LoadSearchResults(
        val request: SearchRequest,
        val requestType: SearchRequestType
    ) : SearchAction()

    data class SearchLoadingSucceeded(
        val requestType: SearchRequestType,
        val loadedPage: Int,
        val nextPage: Int,
        val lastPage: Int,
        val repositories: List<Repository>
    ) : SearchAction()

    data class SearchLoadingFailed(
        val requestType: SearchRequestType,
        val error: Throwable
    ) : SearchAction()

    data class SuggestionsLoadingSucceeded(
        val suggestions: List<String>
    ) : SearchAction()
}
