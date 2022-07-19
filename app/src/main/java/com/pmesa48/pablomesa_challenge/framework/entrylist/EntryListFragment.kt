package com.pmesa48.pablomesa_challenge.framework.entrylist

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmesa48.pablomesa_challenge.R
import com.pmesa48.pablomesa_challenge.databinding.FragmentEntryListBinding
import com.pmesa48.pablomesa_challenge.framework.common.LocationPermissionManager
import com.pmesa48.pablomesa_challenge.framework.common.LocationPermissionManager.PermissionState.GRANTED
import com.pmesa48.pablomesa_challenge.framework.common.LocationPermissionManager.PermissionState.SHOW_RATIONALE
import com.pmesa48.pablomesa_challenge.framework.common.makeGone
import com.pmesa48.pablomesa_challenge.framework.common.makeVisible
import com.pmesa48.pablomesa_challenge.framework.entrylist.EntryListViewModel.UiState.*
import com.pmesa48.pablomesa_challenge.framework.tracker.TrackerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class EntryListFragment : Fragment() {

    companion object {
        fun newInstance() = EntryListFragment()
    }

    private var menuProvider: MenuProvider? = null
    private lateinit var entriesJob: Job
    private lateinit var adapter: EntryAdapter
    private lateinit var _binding: FragmentEntryListBinding

    private val binding get() = _binding
    private val vm: EntryListViewModel by viewModels()

    @Inject
    lateinit var permissionManager: LocationPermissionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryListBinding.inflate(inflater)
        when (activity?.let { activity -> permissionManager.hasLocationPermission(activity) }) {
            GRANTED -> Unit
            SHOW_RATIONALE -> showRationale()
            else -> askPermissions()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EntryAdapter()
        with(binding) {
            locationRecyclerView.adapter = adapter
            locationRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launchWhenStarted {
            vm.serviceStatus.collect(::renderState)
        }
    }

    private fun createMenu(shouldShowStart: Boolean = true) {
        val menuHost: MenuHost = requireActivity()
        menuProvider?.let { menuHost.removeMenuProvider(it) }
        menuProvider = createMenuProvider(shouldShowStart)
        menuHost.addMenuProvider(menuProvider!!, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun createMenuProvider(shouldShowStart: Boolean) = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_entry_list_fragment, menu)
            menu.findItem(R.id.start_action).isVisible = shouldShowStart
            menu.findItem(R.id.stop_action).isVisible = !shouldShowStart
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.start_action -> {
                    checkPermissionsAndStartService()
                    true
                }
                R.id.stop_action -> {
                    stopService()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkPermissionsAndStartService() {
        activity?.let {
            if (permissionManager.hasLocationPermission(it) == GRANTED) startService()
            else showRationale()
        }
    }

    private fun startService() {
        val intentService = Intent(activity, TrackerService::class.java)
        activity?.startService(intentService)
        entriesJob = lifecycleScope.launchWhenStarted { vm.updates.collect(::renderState) }
        entriesJob.start()
    }

    private fun stopService() {
        Intent(activity, TrackerService::class.java)
            .apply { action = TrackerService.STOP_ACTION }
            .let {
                activity?.startService(it)
                entriesJob.cancel()
            }
    }

    private fun askPermissions() {
        permissionManager.requestPermission(
            registerForActivityResult(RequestMultiplePermissions()) { permissions ->
                Log.d("PERMISSION",permissions.toString())
                when {
                    permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> startService()
                    else -> showRationale()
                }
            })
    }

    private fun showRationale() {
        Toast.makeText(
            context,
            R.string.you_need_to_provide_location,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun renderState(it: EntryListViewModel.UiState) {
        when (it) {
            is Stopped -> renderStoppedState()
            is OnUpdate -> renderUpdate(it)
            is Updating -> renderUpdating()
            is Empty -> renderEmptyState()
        }
    }

    private fun renderEmptyState() = with(binding) {
        emptyText.makeVisible()
        renderStopAction()
        emptyText.text = getString(R.string.walking_workout_start_moving_text)
    }

    private fun renderUpdating() = with(binding) {
        emptyText.makeGone()
        renderStopAction()
        locationRecyclerView.makeVisible()
    }

    private fun renderUpdate(uiState: OnUpdate) = with(binding) {
        emptyText.makeGone()
        renderStopAction()
        locationRecyclerView.makeVisible()
        adapter.submitList(uiState.entries)
        locationRecyclerView.smoothScrollToPosition(0)
    }

    private fun renderStoppedState() = with(binding) {
        emptyText.makeVisible()
        renderStartAction()
        emptyText.text = getString(R.string.walking_workout_begin_text)
        locationRecyclerView.makeGone()
    }

    private fun renderStopAction() {
        createMenu(shouldShowStart = false)
    }

    private fun renderStartAction() {
        createMenu(shouldShowStart = true)
    }
}
