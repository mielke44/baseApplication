package br.com.wilson.mielke.baseapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.wilson.mielke.baseapplication.config.models.EventModel
import br.com.wilson.mielke.baseapplication.databinding.EventListFragmentBinding
import br.com.wilson.mielke.baseapplication.ui.adapter.EventListAdapter
import br.com.wilson.mielke.baseapplication.utils.RecyclerItemSpacingDecoration
import br.com.wilson.mielke.baseapplication.viewModel.SecondArchitectureViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EventListFragment: Fragment() {

    private var eventList : List<EventModel>? = null
    private var binding : EventListFragmentBinding? = null
    private val baseViewModel: SecondArchitectureViewModel by sharedViewModel()

    private val eventsAdapter by lazy {
        eventList?.let{
            EventListAdapter(it){eventItem ->
                startEventDetailView(eventItem)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EventListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel.eventList.observe(viewLifecycleOwner, { events ->
            eventList = events
            buildRecyclerView()
            (activity as? MainActivity)?.stopLoader()
        })
    }

    private fun buildRecyclerView(){
        binding?.apply{
            eventRecycler.layoutManager = LinearLayoutManager(context)
            eventRecycler.adapter = eventsAdapter
            eventRecycler.addItemDecoration(RecyclerItemSpacingDecoration((60)))
        }
    }

    private fun startEventDetailView(model: EventModel){
        (activity as? MainActivity)?.startEventDetailFragment(model.id.toInt())
    }

    companion object{
        fun newInstance(): EventListFragment = EventListFragment()
    }
}