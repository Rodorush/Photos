package br.edu.ifsp.scl.sdm.photos.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.photos.R
import br.edu.ifsp.scl.sdm.photos.adapter.ProductAdapter
import br.edu.ifsp.scl.sdm.photos.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.photos.model.JSONPlaceholder
import br.edu.ifsp.scl.sdm.photos.model.Product
import com.android.volley.toolbox.ImageRequest

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val productList: MutableList<Product> = mutableListOf()
    private val productAdapter: ProductAdapter by lazy {
        ProductAdapter(this, productList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        amb.productsSp.apply {
            adapter = productAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    retrieveProductImages(productList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // NSA
                }
            }
        }

        retrieveProducts()
    }

    private fun retrieveProducts() =
        JSONPlaceholder.ProductListRequest({ productList ->
            productList.also {
                productAdapter.addAll(it)
            }
        }, {
            Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT).show()
        }).also {
            JSONPlaceholder.getInstance(this).addToRequestQueue(it)
        }

    private fun retrieveProductImages(product: Product) {
        imageRequest(product.url, amb.productImageIv)
        imageRequest(product.thumbnailUrl, amb.productThumbnailIv)
    }

    private fun imageRequest(url: String, imageView: ImageView) {
        ImageRequest(
            url,
            { response ->
                imageView.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            Bitmap.Config.ARGB_8888,
            {
                Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT)
                    .show()
            }
        ).also {
            JSONPlaceholder.getInstance(this).addToRequestQueue(it)
        }
    }
}