package com.hfad.convertjtop.model

import java.io.File
import java.io.FilenameFilter

class Repo {

    private var listRepo = mutableListOf<ImgLocal>()

    fun getImg() = listRepo

    //метод возвращает список jpg фалов

    fun readFile() {
        val files = File("/storage/emulated/0")

        var filter = object : FilenameFilter {
            override fun accept(dir: File?, name: String?): Boolean {
                if (name?.substring(name.length - 3, name.length).equals("jpg")) {
                    listRepo.add(ImgLocal(name.toString()))
                    return true
                } else {
                    return false
                }
            }
        }
        files.list(filter)
    }

}