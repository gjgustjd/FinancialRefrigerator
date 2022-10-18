package com.study.presentation.v2.view.crawlRecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.domain.model.WebLinkItem
import com.study.presentation.R
import com.study.presentation.databinding.ActivitySearchRecipesBinding
import com.study.presentation.v2.base.BaseActivity
import com.study.presentation.v2.view.searchRecipe.SearchRecipesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CrawlRecipesActivity : BaseActivity<ActivitySearchRecipesBinding,CrawlRecipesViewModel>() {
    private val recyclerAdapter by lazy { RecyclerWebLinkAdapter() }
    var webLinkVOList = arrayListOf<WebLinkItem>()
    override val viewModel:CrawlRecipesViewModel by viewModels()

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
                layoutManager = LinearLayoutManager(baseContext)
            }
            titleBar.run {
                activity = this@CrawlRecipesActivity
                txtHomeTitle.text = getString(R.string.search_result, keyword)
            }
            webLinkVOList.clear()
            recyclerAdapter.notifyDataSetChanged()

            viewModel.setupRecipesDataByIngredient(keyword)
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.webLinks.collectLatest {
                    if(!webLinkVOList.contains(it)) {
                        webLinkVOList.add(it)
                        withContext(Dispatchers.Main)
                        {
                            recyclerAdapter.setInsertItems(webLinkVOList)
                        }
                    }
                }
            }
        }
    }

    override fun observeData() {
    }
}