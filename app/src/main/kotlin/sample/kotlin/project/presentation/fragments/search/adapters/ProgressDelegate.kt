package sample.kotlin.project.presentation.fragments.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import sample.kotlin.project.R
import sample.kotlin.project.domain.pojo.search.RepositoryItem
import sample.kotlin.project.domain.pojo.search.RepositoryProgressItem
import sample.kotlin.project.presentation.core.adapters.BaseAdapterDelegate

internal class ProgressDelegate :
    BaseAdapterDelegate<RepositoryItem, RepositoryItem, ProgressViewHolder>() {

    override fun isForViewType(item: RepositoryItem) = item.value is RepositoryProgressItem

    override fun onCreateViewHolder(parent: ViewGroup): ProgressViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository_progress, parent, false)
        return ProgressViewHolder(view)
    }
}
