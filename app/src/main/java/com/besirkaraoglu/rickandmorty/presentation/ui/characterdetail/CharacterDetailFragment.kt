package com.besirkaraoglu.rickandmorty.presentation.ui.characterdetail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.Episode
import com.besirkaraoglu.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.besirkaraoglu.rickandmorty.util.DataState
import com.besirkaraoglu.rickandmorty.util.loadWithPicasso
import com.besirkaraoglu.rickandmorty.util.showIf
import com.besirkaraoglu.rickandmorty.util.showSnackBarWithAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    private lateinit var episodeList: List<Episode>
    private lateinit var characterEpisodeList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val expandableListAdapter = ExpandableListAdapter()
        with(binding.expandableListView){
            setAdapter(expandableListAdapter)
        }
        viewModel.getAllEpisodes()
        viewModel.getCharacterById(args.id)

        viewModel.character.observe(viewLifecycleOwner){ state ->
            with(binding){
                loading.showIf { state is DataState.Loading }
                success.showIf { state is DataState.Success }
            }
            when(state){
                is DataState.Success -> {
                    initCharacter(state.data)
                    characterEpisodeList = state.data.episode
                    setEpisodes(expandableListAdapter)}
                is DataState.Failure -> {
                    Log.d("TAG", "onViewCreated:character ${state.e.message}")
                    showSnackBarWithAction(
                        requireView(), "${state.e.message}", characterSnackbarClick(),
                        "RETRY"
                    )
                }
            }
        }

        viewModel.episodes.observe(viewLifecycleOwner){ state ->
            when(state){
                is DataState.Success -> {
                        episodeList = state.data
                        setEpisodes(expandableListAdapter)}
                is DataState.Failure -> {
                    Log.d("TAG", "onViewCreated:episode ${state.e.message}")
                    showSnackBarWithAction(
                        requireView(), "${state.e.message}", episodeSnackbarClick(),
                        "RETRY"
                    )
                }
            }
        }
    }
    private fun episodeSnackbarClick() = View.OnClickListener {
        viewModel.getAllEpisodes()
    }
    private fun characterSnackbarClick() = View.OnClickListener {
        viewModel.getCharacterById(args.id)
    }
    private fun initCharacter(character: Character){
        with(binding){
            iv.loadWithPicasso(character.image)
            tvName.text = character.name
            tvSituation.text = SpannableStringBuilder().append(character.status)
                .append(",")
                .append(character.species)
            tvGender.text = character.gender
        }
    }
    private fun setEpisodes(expandableListAdapter: ExpandableListAdapter){
        if (this::episodeList.isInitialized &&
                this::characterEpisodeList.isInitialized){
            val list: MutableList<Episode> = mutableListOf()
            for (i in episodeList){
                if (characterEpisodeList.contains(i.url)){
                    list.add(i)
                }
            }
            expandableListAdapter.setList(list)
        }
    }
}