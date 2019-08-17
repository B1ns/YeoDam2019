package com.yeodam.yeodam2019.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.yeodam.yeodam2019.R

class customUtils
    (context: Context, googleMap: GoogleMap, clusterManager: ClusterManager<job>) : DefaultClusterRenderer<job>(
    context,
    googleMap,
    clusterManager
) {

    private var iconGenerator: IconGenerator? = null
    private var clusterIconGenerator: IconGenerator? = null
    private var imageView: ImageView? = null
    private var clusterImageView: ImageView? = null
    private var markerWidth: Int? = null
    private var markerHeight: Int? = null
    private val TAG: String = "ClusterRenderer"
    private var options: DisplayImageOptions? = null

    init {

        // 클러스터 아이콘 생성자 초기화
        clusterIconGenerator = IconGenerator(context.applicationContext)
        val clusterView = LayoutInflater.from(context).inflate(R.layout.custom_marker, null)
        clusterIconGenerator!!.setContentView(clusterView)
        clusterImageView = clusterView.findViewById(R.id.custom_Image)

        // 클러스터 항목 아이콘 생성자 초기화
        iconGenerator = IconGenerator(context.applicationContext)
        imageView = ImageView(context.applicationContext)
        markerWidth = context.resources.getDimension(R.dimen.custom_profile_image).toInt()
        markerHeight = context.resources.getDimension(R.dimen.custom_profile_image).toInt()
        imageView!!.layoutParams = ViewGroup.LayoutParams(markerWidth!!, markerHeight!!)
        iconGenerator!!.setContentView(imageView)

        options = DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_logo_yeodam)
            .showImageForEmptyUri(R.drawable.ic_logo_yeodam)
            .showImageOnFail(R.drawable.ic_logo_yeodam)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build()
    }

    override fun onBeforeClusterItemRendered(jobItem: job, markerOptions: MarkerOptions?) {
        ImageLoader.getInstance().displayImage(jobItem.getUri(), imageView, options)
//        var icon = iconGenerator?.makeIcon(job.getName())
//        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(job.getName())
    }

    override fun onBeforeClusterRendered(cluster: Cluster<job>?, markerOptions: MarkerOptions?) {
        var iterator = cluster?.items?.iterator()
        ImageLoader.getInstance().displayImage(iterator?.next()?.getUri(), clusterImageView, options)
        var icon = clusterIconGenerator?.makeIcon(iterator?.next()?.getName())
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<job>?): Boolean {
        return cluster!!.size > 1
    }
}