package com.onionsquare.goabase.feature.parties

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.parties_map.*


class PartiesMapActivity : BaseActivity(), OnMapReadyCallback {

    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        val clusterManager = ClusterManager<PartyMarker>(this, googleMap)
        googleMap?.let {
            it.setOnCameraIdleListener(clusterManager)
            it.setOnMarkerClickListener(clusterManager)
            it.setOnInfoWindowClickListener(clusterManager)

            val list = intent.getSerializableExtra("PARTIES") as ArrayList<Party>

            list.forEach {
                if (!it.geoLat.isNullOrEmpty() && !it.geoLon.isNullOrEmpty()){
                    val marker = PartyMarker(LatLng(it.geoLat.toDouble(), it.geoLon.toDouble()), it.nameParty, "")
                    clusterManager.addItem(marker)
                }

            }

            clusterManager.cluster()
        }
    }

    private fun displayMarker() {

    }

    override fun provideLayout(): Int {
        return R.layout.parties_map
    }

    override fun provideToolbar(): Toolbar? {
        return custom_toolbar as Toolbar
    }
}