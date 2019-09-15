package com.yeodam.yeodam2019.view.adapter

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.view.fragment.map.MapMoreAllFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMoreMemoFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePayFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePhotoFragment

@Suppress("DEPRECATION")
class MapMoreAdapter(
    fm: FragmentManager,
    memo: ArrayList<String>,
    memoLocation: ArrayList<LatLng>,
    photo: ArrayList<Bitmap>,
    photoLoaction: ArrayList<LatLng>,
    pay: ArrayList<String>,
    payInfo: ArrayList<String>,
    payLocation: ArrayList<LatLng>
) : FragmentStatePagerAdapter(fm) {


    private val fragmentTitleList = mutableListOf("전체", "사진", "메모", "경비")

    val Memo = memo
    val MemoLocation = memoLocation
    val Photo = photo
    val PhotoLocation = photoLoaction
    val Pay = pay
    val PayInfo = payInfo
    val PayLocation = payLocation

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragment = MapMoreAllFragment()
                val bundle = Bundle()

                bundle.putStringArrayList("Memo", Memo)
                bundle.putParcelableArrayList("MemoLocation", MemoLocation)

                bundle.putStringArrayList("Pay", Pay)
                bundle.putStringArrayList("PayInfo", PayInfo)
                bundle.putParcelableArrayList("PayLocation", PayLocation)

                bundle.putParcelableArrayList("Photo", Photo)
                bundle.putParcelableArrayList("PhotoLocation", PhotoLocation)

                fragment.arguments = bundle

                return fragment
            }

            1 -> {
                val fragmentPhoto = MapMorePhotoFragment()
                val bundlePhoto = Bundle()

                bundlePhoto.putParcelableArrayList("Photo", Photo)
                bundlePhoto.putParcelableArrayList("PhotoLocation", PhotoLocation)

                fragmentPhoto.arguments = bundlePhoto

                return fragmentPhoto
            }

            2 -> {
                val fragmentMemo = MapMoreMemoFragment()
                val bundleMemo = Bundle()

                bundleMemo.putStringArrayList("Memo", Memo)
                bundleMemo.putParcelableArrayList("MemoLocation", MemoLocation)

                fragmentMemo.arguments = bundleMemo

                return fragmentMemo
            }

            3 -> {
                val fragmentPay = MapMorePayFragment()
                val bundlePay = Bundle()

                bundlePay.putStringArrayList("Pay", Pay)
                bundlePay.putStringArrayList("PayInfo", PayInfo)
                bundlePay.putParcelableArrayList("PayLocation", PayLocation)

                fragmentPay.arguments = bundlePay

                return fragmentPay
            }

            else -> {
                val fragment = MapMoreAllFragment()
                val bundle = Bundle()

                bundle.putStringArrayList("Memo", Memo)
                bundle.putParcelableArrayList("MemoLocation", MemoLocation)

                bundle.putStringArrayList("Pay", Pay)
                bundle.putStringArrayList("PayInfo", PayInfo)
                bundle.putParcelableArrayList("PayLocation", PayLocation)

                bundle.putParcelableArrayList("Photo", Photo)
                bundle.putParcelableArrayList("PhotoLocation", PhotoLocation)

                fragment.arguments = bundle

                return fragment
            }
        }
    }

    override fun getCount() = 4

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

}