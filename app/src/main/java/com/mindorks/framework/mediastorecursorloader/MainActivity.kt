package com.mindorks.framework.mediastorecursorloader

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val IMAGE_LOADER_ID = 1
    private val listOfAllImages = ArrayList<MediaData>()

    var projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_TAKEN
    )

//    var selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
//            + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
    val selection: String? = null     //Selection criteria

    var queryUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        LoaderManager.getInstance(this).initLoader(IMAGE_LOADER_ID,null,this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this,
            queryUri,
            projection,
            selection,
            null,
            MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        listOfAllImages.clear()
        if(data != null){
            data.moveToFirst()
            while (!data.isAfterLast){

                val simpleDateFormat = SimpleDateFormat("dd - MM - yyyy hh : mm : ss")
                val dateString = simpleDateFormat.format(data.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN).toLong())
                listOfAllImages.add(
                    MediaData(
                        Uri.withAppendedPath(queryUri, "" + data.getLong(data.getColumnIndex(MediaStore.Images.Media._ID))).toString(),
                        dateString
                    )
                )
                data.moveToNext()
            }

            rv_image.apply {
                adapter = RvAdapter(listOfAllImages)
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        rv_image.adapter?.notifyDataSetChanged()
    }
}