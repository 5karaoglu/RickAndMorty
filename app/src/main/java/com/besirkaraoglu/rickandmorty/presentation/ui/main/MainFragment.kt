package com.besirkaraoglu.rickandmorty.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.databinding.FragmentMainBinding
import com.besirkaraoglu.rickandmorty.util.DEFAULT_MARGIN
import com.besirkaraoglu.rickandmorty.util.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(),
    MainAdapter.ClickListener {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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
        val footerAdapter = CharacterLoadStateAdapter(pagingAdapter::retry)
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position == pagingAdapter.itemCount  && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }

        }
        with(binding.recycler){
            adapter = pagingAdapter.withLoadStateFooter(footerAdapter)
            layoutManager = gridLayoutManager
            addItemDecoration(GridItemDecoration(DEFAULT_MARGIN))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.collectLatest {
                pagingAdapter.submitData(it)
            }
        }

    }

    override fun itemClicked(item: Character) {
       val action = MainFragmentDirections.actionMainFragmentToCharacterDetailFragment(item.id)
       findNavController().navigate(action)
    }
}
