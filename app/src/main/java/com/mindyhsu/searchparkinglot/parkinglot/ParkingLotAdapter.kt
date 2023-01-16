package com.mindyhsu.searchparkinglot.parkinglot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.ParkingLotDisplayInfo
import com.mindyhsu.searchparkinglot.databinding.ListItemParkingLotInfoBinding

class ParkingLotAdapter :
    ListAdapter<ParkingLotDisplayInfo, ParkingLotAdapter.ParkingLotViewHolder>(
        ParkingLotDiffCallback()
    ) {

    class ParkingLotViewHolder(private var binding: ListItemParkingLotInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ParkingLotDisplayInfo) {
            binding.infoIdText.text =
                AppApplication.instance.getString(R.string.parking_lot_id, item.id)
            binding.infoNameText.text =
                AppApplication.instance.getString(R.string.parking_lot_name, item.name)
            binding.infoAddressText.text =
                AppApplication.instance.getString(R.string.parking_lot_address, item.address)
            binding.infoTotalCarText.text =
                AppApplication.instance.getString(
                    R.string.parking_lot_total_car,
                    item.totalCar.toString()
                )

            // available
            if (item.availableCar > -1) {
                binding.infoAvailableCarText.text = AppApplication.instance.getString(
                    R.string.parking_lot_available_car,
                    item.availableCar.toString()
                )
            }
            if (item.statusCharging > -1 && item.statusStandby > -1) {
                binding.infoChargeStationText.visibility = View.VISIBLE
                binding.infoStatusChargingText.visibility = View.VISIBLE
                binding.infoStatusStandbyText.visibility = View.VISIBLE
                binding.infoStatusChargingText.text = AppApplication.instance.getString(
                    R.string.charge_station_status_charging,
                    item.statusCharging.toString()
                )
                binding.infoStatusStandbyText.text = AppApplication.instance.getString(
                    R.string.charge_station_status_standby,
                    item.statusStandby.toString()
                )
            } else {
                binding.infoChargeStationText.visibility = View.GONE
                binding.infoStatusChargingText.visibility = View.GONE
                binding.infoStatusStandbyText.visibility = View.GONE
            }
        }
    }

    class ParkingLotDiffCallback : DiffUtil.ItemCallback<ParkingLotDisplayInfo>() {
        override fun areItemsTheSame(
            oldItem: ParkingLotDisplayInfo,
            newItem: ParkingLotDisplayInfo
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ParkingLotDisplayInfo,
            newItem: ParkingLotDisplayInfo
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingLotViewHolder {
        return ParkingLotViewHolder(
            ListItemParkingLotInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ParkingLotViewHolder, position: Int) {
        val parkingLot = getItem(position)
        holder.bind(parkingLot)
    }
}
