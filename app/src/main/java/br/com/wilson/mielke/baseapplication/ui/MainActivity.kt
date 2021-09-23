package br.com.wilson.mielke.baseapplication.ui

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.wilson.mielke.baseapplication.R
import br.com.wilson.mielke.baseapplication.databinding.ActivityMainBinding
import br.com.wilson.mielke.baseapplication.utils.CustomDialog
import br.com.wilson.mielke.baseapplication.utils.fragmentTransaction
import br.com.wilson.mielke.baseapplication.utils.ProgressDialog
import br.com.wilson.mielke.baseapplication.viewModel.SecondArchitectureViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val baseViewModel: SecondArchitectureViewModel by viewModel()
    private val progressDialog by lazy { ProgressDialog(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(binding.root)

        setupListeners()
        startEventListFragment()
        startLoader()
    }

    fun updateUserIcon(){
        if(baseViewModel.isUserUpdated()) binding.toolbarUserButton.setImageResource(R.drawable.ic_confirm)
    }

    private fun setupListeners(){
        binding.apply{
            toolbarButton.setImageResource(R.drawable.ic_close)
            toolbarButton.setOnClickListener {
                CustomDialog.Builder(this@MainActivity)
                    .title(R.string.finish_app_dialog_title)
                    .message(R.string.finish_app_dialog_message)
                    .primaryButton(R.string.yes,null){
                        finishAffinity()
                    }
                    .image(R.drawable.ic_shutdown)
                    .secondaryButton(R.string.no, null)
                    .build()
                    .show()
            }
            toolbarUserButton.setOnClickListener {

                CustomDialog.Builder(this@MainActivity)
                    .title(R.string.user_update_dialog_title)
                    .message(R.string.user_update_dialog_message)
                    .primaryButton(R.string.yes,null){
                        startUserDataFragment()
                    }
                    .image(R.drawable.ic_person)
                    .secondaryButton(R.string.no, null){}
                    .build()
                    .show()
            }
        }
    }

    fun startEventDetailFragment(id: Int){
        binding.toolbarUserButton.visibility = View.VISIBLE
        setHomeToolbarButton()
        addFragment(EventDetailsFragment.newInstance(id))
    }

    private fun startEventListFragment(){
        setupListeners()
        binding.toolbarUserButton.visibility = View.VISIBLE
        addFragment(EventListFragment.newInstance())
    }

    fun startUserDataFragment(){
        binding.toolbarUserButton.visibility = View.GONE
        setHomeToolbarButton()
        addFragment(UserDataFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment) {
        fragmentTransaction(R.id.fragmentContainer) {
            add(fragment, false)
        }
    }

    private fun clear() {
        fragmentTransaction {
            clear()
        }
    }

    fun startLoader(){
        progressDialog.show()
    }

    fun stopLoader(){
        progressDialog.dismiss()
    }

    private fun setHomeToolbarButton(){
        binding.toolbarButton.apply{
            setImageResource(R.drawable.ic_home)
            setOnClickListener {
                clear()
                startEventListFragment()
            }
        }
    }
}