package com.prismsoft.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.prismsoft.foody.ui.viewmodels.MainViewModel
import com.prismsoft.foody.adapters.RecipeListAdapter
import com.prismsoft.foody.data.NetworkResult
import com.prismsoft.foody.databinding.FragmentRecipesBinding
import com.prismsoft.foody.ui.viewmodels.RecipesViewModel
import com.prismsoft.foody.util.Constants.Companion.API_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var recipesViewModel: RecipesViewModel
    private lateinit var binding: FragmentRecipesBinding
    private val adapter by lazy {
        RecipeListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesBinding.inflate(inflater)
        binding.shimmerRecyclerViewCpy.showShimmerAdapter()
        setupAdapter()
        requestApiData()
        return binding.root
    }

    private fun requestApiData() {
        viewModel.getRecipes(
            requireContext(),
            recipesViewModel.applyQueries()
        )

        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> showShimmerEffect()
            }
        }
    }

    private fun setupAdapter() {
        binding.shimmerRecyclerViewCpy.apply {
            adapter = this@RecipesFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            showShimmerAdapter()
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerRecyclerViewCpy.showShimmerAdapter()
    }

    private fun hideShimmerEffect() {
        binding.shimmerRecyclerViewCpy.hideShimmerAdapter()
    }
}