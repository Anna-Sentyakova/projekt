package sample.kotlin.project.presentation.fragments.search.state

import sample.kotlin.project.domain.core.Mapper
import sample.kotlin.project.domain.stores.search.entities.SearchState
import javax.inject.Inject

class SearchStateToParcelableMapper
@Inject constructor(
) : Mapper<SearchState, SearchStateParcelable> {

    override fun map(from: SearchState) =
        SearchStateParcelable(
            loading = from.loading,
            data = from.data,
            suggestions = from.suggestions
        )
}
