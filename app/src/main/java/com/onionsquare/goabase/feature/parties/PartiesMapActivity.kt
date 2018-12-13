package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
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

        val latZoom = intent.getStringExtra("ZOOM_POS_LAT")?.toDouble()
        val longZoom = intent.getStringExtra("ZOOM_POS_LONG")?.toDouble()
        if (latZoom != null && longZoom != null) {
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latZoom, longZoom), 6f))
        }

        val clusterManager = ClusterManager<PartyMarker>(this, googleMap)
        clusterManager.renderer = CustomClusterRenderer(this, googleMap!!, clusterManager)
        googleMap?.let {
            val list = intent.getSerializableExtra("PARTIES") as ArrayList<Party>

            it.setOnCameraIdleListener(clusterManager)
            it.setOnMarkerClickListener(clusterManager)
            it.setOnInfoWindowClickListener(clusterManager)

            clusterManager.setOnClusterInfoWindowClickListener {
                val filteredList = arrayListOf<Party>()
                it.items?.forEach {
                    for (p in list) {
                        if (p.id == it.partyId) {
                            filteredList.add(p)
                        }
                    }
                }
                val intent: Intent = Intent(this@PartiesMapActivity, PartiesOfflineActivity::class.java)
                intent.putExtra("PARTIES_OFFLINE", filteredList)
                startActivity(intent)
            }
            clusterManager.setOnClusterClickListener {
                false
            }
            clusterManager.setOnClusterItemInfoWindowClickListener {
                val intent: Intent = Intent(this@PartiesMapActivity, PartyDetailsActivity::class.java)
                intent.putExtra(PartiesActivity.PARTY_ID_EXTRA, it.partyId)
                startActivity(intent)
            }

            list.forEach {
                if (!it.geoLat.isNullOrEmpty() && !it.geoLon.isNullOrEmpty()) {
                    val marker = PartyMarker(it.id, LatLng(it.geoLat.toDouble(), it.geoLon.toDouble()), it.nameParty, "", it.nameType)

                    clusterManager.addItem(marker)
                }
            }
            clusterManager.cluster()
        }
    }

    override fun provideLayout(): Int {
        return R.layout.parties_map
    }

    override fun provideToolbar(): Toolbar? {
        return custom_toolbar as Toolbar
    }
}