package com.besirkaraoglu.rickandmorty.presentation.ui.characterdetail

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.Episode
import com.besirkaraoglu.rickandmorty.databinding.ElvItemBinding
import com.besirkaraoglu.rickandmorty.databinding.ElvSubItemBinding


class ExpandableListAdapter: BaseExpandableListAdapter() {

    private var list = listOf<Episode>()
    fun setList(list: List<Episode>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun getGroupCount(): Int = 1

    override fun getChildrenCount(groupPosition: Int): Int  = list.size

    override fun getGroup(groupPosition: Int): Any = "Episodes"

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return list[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long = 0

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ElvItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        binding.tv.text = SpannableStringBuilder().append("Episodes")
        binding.indicator.isSelected = isExpanded
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = ElvSubItemBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        val episode = list[childPosition]
        binding.tv.text = SpannableStringBuilder().append(episode.name).append(" (").append(episode.episode).append(")")
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false
}