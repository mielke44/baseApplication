package br.com.wilson.mielke.baseapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.wilson.mielke.baseapplication.utils.CustomDialog
import br.com.wilson.mielke.baseapplication.R
import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.databinding.DetailEventFragmentBinding
import br.com.wilson.mielke.baseapplication.viewModel.SecondArchitectureViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment: Fragment(), OnMapReadyCallback{

    private var binding: DetailEventFragmentBinding? = null
    private var singleEvent: EventModel? = null
    private val baseViewModel: SecondArchitectureViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetailEventFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            singleEvent?.id = it.getInt(EVENT_ID).toString()
            baseViewModel.getSingleEvent(it.getInt(EVENT_ID))
        }
        baseViewModel.singleEvent.observe(viewLifecycleOwner, Observer<EventModel>{
            singleEvent = it
            setupView()
            binding?.map?.let{ view ->
                val mapFragment = childFragmentManager.findFragmentById(view.id) as? SupportMapFragment
                mapFragment?.getMapAsync(this)
            }
            (activity as? MainActivity)?.stopLoader()
        })
        singleEvent?.id?.let{ baseViewModel.getSingleEvent(it.toInt()) }
        (activity as? MainActivity)?.startLoader()
    }

    private fun openUserDataScreen(){
        (activity as? MainActivity)?.startUserDataFragment()
    }

    private fun setupView(){
        binding?.apply{
            singleEvent?.let{
                eventTitle.text = it.title
                eventDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date(it.date))
                eventDescription.text = it.description
                if(baseViewModel.isEventChecked(it.id.toInt())){
                    binding?.registerButton?.isEnabled = false
                }
                context?.let{context ->
                    eventPrice.text = context.getString(R.string.event_price, it.price.toString())
                    Glide.with(context)
                        .load(it.image)
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_error)
                        .centerCrop()
                        .into(eventImage)
                }
            }

            registerButton.setOnClickListener {
                if(!baseViewModel.isUserUpdated()) {
                    context?.let {
                        CustomDialog.Builder(it)
                            .title(R.string.user_not_updated_dialog_title)
                            .message(R.string.user_not_updated_dialog_message)
                            .image(R.drawable.ic_warning)
                            .primaryButton(R.string.yes) {
                                openUserDataScreen()
                            }.secondaryButton(R.string.not_now)
                            .build()
                            .show()
                    }
                }else {
                    singleEvent?.id?.let{
                        baseViewModel.sendCheckingUser(it)
                        baseViewModel.postState.observe(viewLifecycleOwner, { response ->
                            if(response.code.isEmpty()){
                                context?.let{
                                    CustomDialog.Builder(it)
                                        .title(R.string.error_network_title)
                                        .message(R.string.error_network_message)
                                        .image(R.drawable.ic_warning)
                                        .primaryButton(R.string.try_again){}
                                        .build()
                                        .show()
                                }
                            }else{
                                (activity as? MainActivity)?.stopLoader()
                                context?.let{
                                    CustomDialog.Builder(it)
                                        .title(R.string.registered_dialog_title)
                                        .message(R.string.registered_dialog_message)
                                        .image(R.drawable.ic_happy)
                                        .primaryButton(R.string.ok){
                                            binding?.registerButton?.isEnabled = false
                                        }
                                        .build()
                                        .show()
                                }
                            }
                        })
                        (activity as? MainActivity)?.startLoader()
                    }
                }
            }

            locationButton.setOnClickListener {
                mapContainer.visibility = View.VISIBLE
            }

            closeButton.setOnClickListener {
                mapContainer.visibility = View.GONE
            }
        }
    }

    companion object{

        const val EVENT_ID = "EVENT_ID"

        fun newInstance(id :Int): EventDetailsFragment {
            val bundle = Bundle().apply{
                putInt(EVENT_ID, id)
            }
            return EventDetailsFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(10.toFloat()))
        singleEvent?.let{ event ->
            val eventLocation = LatLng(event.latitude, event.longitude)
            googleMap?.addMarker(
                MarkerOptions()
                    .position(eventLocation)
                    .title(event.title)
            )
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15.toFloat()))
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(10.toFloat()))
        }
    }
}