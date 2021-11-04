package com.hfad.convertjtop.view

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.convertjtop.adapter.MainAdapter
import com.hfad.convertjtop.databinding.FragmentListBinding
import com.hfad.convertjtop.model.Repo
import com.hfad.convertjtop.preserter.MainPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class ListFileFragment : MvpAppCompatFragment(), MainView {
    val PERMISSIONS_REQUEST_READ = 20
    val presenter: MainPresenter by moxyPresenter {
        MainPresenter(Repo())
    }


    var adapter: MainAdapter? = null
    private var vb: FragmentListBinding? = null

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentListBinding.inflate(inflater, container, false)

        return vb?.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkPerm()


    }


    @RequiresApi(Build.VERSION_CODES.R)
    fun checkPerm() {
        val permission = arrayOf<String>(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED &&
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } == PackageManager.PERMISSION_GRANTED) {
            Log.i("GRAND", "РазрешениЯ есть")
            vb?.recyclerContainer?.layoutManager = LinearLayoutManager(context)
            adapter = MainAdapter(presenter.fileListLocal)
            vb?.recyclerContainer?.adapter = adapter
        } else {
            requestPermission()
        }
    }

    fun requestPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.i("GRAND", "WRITE_EXTERNAL_STORAGE - ok")

                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к файлам")
                        .setMessage("Объяснение")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermissions(
                                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                PERMISSIONS_REQUEST_READ
                            )
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> { //Запрашиваем разрешение
                    requestPermissions(
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_READ
                    )

                }
            }

        }
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.i("GRAND", "READ_EXTERNAL_STORAGE - ok")

                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к файлам")
                        .setMessage("Объяснение")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermissions(
                                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                                PERMISSIONS_REQUEST_READ
                            )
                        }
                        .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> { //Запрашиваем разрешение

                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_READ
                    )
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("GRAND", "Разрешение есть")

        } else {
            Log.i("GRAND", "Нет разрешения")
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() = ListFileFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null


    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun init() {

    }
}