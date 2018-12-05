package com.onionsquare.goabase.feature.parties

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.onionsquare.goabase.R

class CustomClusterRenderer(val context: Context, val googleMap: GoogleMap, val clusterManager: ClusterManager<PartyMarker>) :
        DefaultClusterRenderer<PartyMarker>(context, googleMap, clusterManager) {

    override fun onBeforeClusterRendered(cluster: Cluster<PartyMarker>?, markerOptions: MarkerOptions?) {
        markerOptions?.title(cluster?.items?.first()?.title)
        markerOptions?.snippet("+ " + (cluster?.items?.size?.minus(1)) + " more")
        super.onBeforeClusterRendered(cluster, markerOptions)
    }


    override fun onBeforeClusterItemRendered(item: PartyMarker, markerOptions: MarkerOptions) {
        when (item.kind) {
            "Indoor" -> markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.dj_pin))
            "Club" -> markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.dj_pin))
            "Open Air" -> markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_pin))
            "Festival" -> markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tente))
            "In- & Outdoor" -> markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_pin))
            else -> {
            }
        }
        markerOptions.visible(true)
    }
}