package tw.edu.nptu.bbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import tw.edu.nptu.bbox.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolBar)
        val drawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment)
        val topLevelDestinations = setOf<Int>(R.id.boxFragment, R.id.bottleList)
        appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestinations).setOpenableLayout(drawerLayout)
                .build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}