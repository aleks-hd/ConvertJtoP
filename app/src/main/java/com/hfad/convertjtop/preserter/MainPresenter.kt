package com.hfad.convertjtop.preserter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log


import com.hfad.convertjtop.model.*
import com.hfad.convertjtop.view.MainView
import moxy.MvpPresenter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainPresenter(val imageRepo: Repo) : MvpPresenter<MainView>() {
    class FileListLocal() : IListFilePresentor {
        val files = mutableListOf<ImgLocal>()

        //   override var itemClickListener: ((IItemView) -> Unit)? = null
        fun convert(name: String): Boolean {
            try {
                var imageConv = FileOutputStream(File("/storage/emulated/0/${name}"))
                var bmp = BitmapFactory.decodeFile("/storage/emulated/0/${name}")
                bmp.compress(Bitmap.CompressFormat.PNG, 100, imageConv)
                // imageConv.flush()
                imageConv.close()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("CLICK", "Ошибка конвертации")
                return false
            }

        }

        override fun bindView(view: IItemView) {
            val file = files[view.pos]
            view.setName(file.name)
        }

        override fun getCount(): Int {
            return files.size
        }

        override fun itemClock(name: String) {
            if (convert(name)) {
                Log.i("CLICK", " ${name} Успешно преобразован")
            } else {
                Log.i("CLICK", "Ошибка")
            }
        }
    }

    val fileListLocal = FileListLocal()

    fun init() {
        imageRepo.readFile()
        val user = imageRepo.getImg()
        fileListLocal.files.addAll(user)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        init()
        viewState.updateList()
    }


}