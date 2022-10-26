package com.study.presentation.v2.view.crawlRecipe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.domain.model.WebLinkItem
import com.study.presentation.R
import com.study.presentation.databinding.ActivitySearchRecipesBinding
import com.study.presentation.v2.base.BaseActivity
import com.study.presentation.v2.view.searchRecipe.SearchRecipesActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CrawlRecipesActivity : BaseActivity<ActivitySearchRecipesBinding, CrawlRecipesViewModel>() {
    private val recyclerAdapter by lazy { RecyclerWebLinkAdapter(this) }
    var webLinkVOList = arrayListOf<WebLinkItem>()
    override val viewModel: CrawlRecipesViewModel by viewModels()

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    companion object IntentKey {
        const val SEARCH_TYPE = "type"
        const val SEARCH_KEYWORD = "keyword"
        const val TYPE_INGREDIENT = "ingredient"
        const val TYPE_RECIPE = "recipe"
    }

    override val layoutId: Int
        get() = R.layout.activity_search_recipes
    lateinit var type: String
    lateinit var keyword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            type = intent.getStringExtra(SearchRecipesActivity.SEARCH_TYPE) ?: ""
            keyword = intent.getStringExtra(SearchRecipesActivity.SEARCH_KEYWORD) ?: ""

            recyclerSearchRecipes.run {
                adapter = recyclerAdapter
                layoutManager = linearLayoutManager
            }
            titleBar.run {
                activity = this@CrawlRecipesActivity
                txtHomeTitle.text = getString(R.string.search_result, keyword)
            }

            lifecycleScope.launchWhenResumed {
                viewModel.setupRecipesDataByIngredient(keyword)
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CrawlRecipeState.Loading -> {
                            pgBarLoading.visibility = View.VISIBLE
                        }
                        is CrawlRecipeState.Success -> {
                            pgBarLoading.visibility = View.GONE
                            if (!webLinkVOList.contains(state.item)) {
                                webLinkVOList.add(state.item)
                                recyclerAdapter.setItems(webLinkVOList)
                            }
                        }
                        is CrawlRecipeState.Error -> {
                            pgBarLoading.visibility = View.GONE
                            Toast.makeText(
                                this@CrawlRecipesActivity,
                                "에러가 발생하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun observeData() {
    }
}