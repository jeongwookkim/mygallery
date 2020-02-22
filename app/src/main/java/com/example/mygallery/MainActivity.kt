package com.example.mygallery

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.jetbrains.anko.*
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.
                PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                alert(" 사진정보 얻을라면 외부 저장소 권하 ㄴ내놔야함 ",
                    "권한 주라하는 이유"){
                    yesButton{
                        ActivityCompat.requestPermissions(this@MainActivity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE)
                    }
                    noButton{}

                }.show()
            }else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        }else{
            getAllPhotos()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode){
                REQUEST_READ_EXTERNAL_STORAGE->{
                if((grantResults.isNotEmpty()
                            &&grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    getAllPhotos()
                }else{
                    toast("권한 거부 됨")
                }
                    return
            }
        }
    }


    private fun getAllPhotos(){
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN+" DESC")
        val fragment = ArrayList<Fragment>()
        if(cursor !=null){
            while(cursor.moveToNext()){
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.
                Media.DATA))
                Log.d("MainActivity",uri)
                fragment.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }
        val adapter =MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragment)
        viewPager.adapter = adapter
    }

}
