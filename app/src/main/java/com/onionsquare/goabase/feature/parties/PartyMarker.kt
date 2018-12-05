package com.onionsquare.goabase.feature.parties

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class PartyMarker(val partyId: String, val pos: LatLng, val tit: String, val snip: String, val kind: String) : ClusterItem {

    override fun getSnippet(): String {
        return snip
    }

    override fun getTitle(): String {
        return tit
    }

    override fun getPosition(): LatLng {
        return pos
    }
}