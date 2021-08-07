package com.besirkaraoglu.rickandmorty.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.besirkaraoglu.rickandmorty.R
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.databinding.FragmentMainBinding
import com.besirkaraoglu.rickandmorty.presentation.RamViewModel
import com.besirkaraoglu.rickandmorty.util.DEFAULT_MARGIN
import com.besirkaraoglu.rickandmorty.util.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(),
MainAdapter.ClickListener{

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagingAdapter = MainAdapter(this)
        with(binding.recycler){
            adapter = pagingAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
            addItemDecoration(GridItemDecoration(DEFAULT_MARGIN))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.collectLatest {
                pagingAdapter.submitData(it)
            }
        }

    }

    override fun itemClicked(item: Character) {

    }
}
