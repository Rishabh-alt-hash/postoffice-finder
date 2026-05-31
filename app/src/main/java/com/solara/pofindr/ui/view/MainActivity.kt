package com.solara.pofindr.ui.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.solara.pofindr.R
import com.solara.pofindr.data.api.ApiService
import com.solara.pofindr.data.db.AppDatabase
import com.solara.pofindr.data.repo.PostOfficeRepository
import com.solara.pofindr.databinding.ActivityMainBinding
import com.solara.pofindr.ui.vm.PostOfficeViewModel
import com.solara.pofindr.ui.vm.PostOfficeViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postOfficeAdapter: PostOfficeAdapter

    private val viewModel: PostOfficeViewModel by viewModels {
        PostOfficeViewModelFactory(
            PostOfficeRepository(
                AppDatabase.getDatabase(this).postOfficeDa(),
                Retrofit.Builder()
                    .baseUrl("http://www.postalpincode.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setUpRecyclerView()

        viewModel.allPostOffices.observe(this) { postOffices ->
            postOffices?.let {
                postOfficeAdapter.submitList(it)
            }
        }

        if (isNetworkAvailable()) {
            viewModel.fetchPostOffices("Vadodara")
        } else {
            viewModel.getLocalPostOffices()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        setUpSearchView(searchView)
        return true
    }


    private fun setUpSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                postOfficeAdapter.filter(newText)
                return false
            }
        })

        val closeButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton.setOnClickListener {
            searchView.setQuery("", false)
            searchView.clearFocus()
        }
    }

    private fun setUpRecyclerView() {
        postOfficeAdapter = PostOfficeAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postOfficeAdapter
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}