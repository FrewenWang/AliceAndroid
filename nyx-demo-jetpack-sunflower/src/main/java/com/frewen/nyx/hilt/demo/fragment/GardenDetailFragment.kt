package com.frewen.nyx.hilt.demo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.nyx.hilt.demo.R
import com.frewen.nyx.hilt.demo.db.entity.Plant
import com.frewen.nyx.hilt.demo.databinding.FragmentPlantDetailBinding
import com.frewen.nyx.hilt.demo.vm.PlantDetailViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GardenDetailFragment : BaseFragment() {
    
    @Inject
    lateinit var plantDetailViewModelFactory: PlantDetailViewModelFactory
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(inflater,
                R.layout.fragment_plant_detail,
                container,
                false
        ).apply {
        
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    
    
    fun interface Callback {
        fun add(plant: Plant?)
    }
    
}
