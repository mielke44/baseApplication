package br.com.wilson.mielke.baseapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.wilson.mielke.baseapplication.R
import br.com.wilson.mielke.baseapplication.databinding.UserDetailFragmentBinding
import br.com.wilson.mielke.baseapplication.utils.CustomDialog
import br.com.wilson.mielke.baseapplication.utils.StringUtils
import br.com.wilson.mielke.baseapplication.viewModel.SecondArchitectureViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserDataFragment: Fragment() {

    private var binding: UserDetailFragmentBinding? = null
    private val baseViewModel: SecondArchitectureViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnUpdate?.setOnClickListener {
            binding?.apply {
                softSaveUserData(nameInput.text.toString(), mailInput.text.toString())
            }
        }
    }

    private fun softSaveUserData(username : String, userMail: String){

        if(!userMail.isNullOrEmpty() && !username.isNullOrEmpty() && StringUtils.isValidEmail(userMail)){
            baseViewModel.saveUserData(username, userMail)
            binding?.errorMessage?.visibility =  View.GONE
            context?.let{
                CustomDialog.Builder(it)
                    .title(R.string.updated)
                    .image(R.drawable.ic_happy)
                    .primaryButton(R.string.ok){
                        (activity as? MainActivity)?.updateUserIcon()
                    }
                    .secondaryButton("")
                    .build()
                    .show()
            }
        }else{
            if(!StringUtils.isValidEmail(userMail)) binding?.errorMessage?.text = getString(R.string.mail_format_error)
            binding?.errorMessage?.visibility =  View.VISIBLE
        }
    }

    companion object{
        fun newInstance() : UserDataFragment = UserDataFragment()
    }
}