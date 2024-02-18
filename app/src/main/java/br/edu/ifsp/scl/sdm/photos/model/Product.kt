package br.edu.ifsp.scl.sdm.photos.model

data class Product(

    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String

//    val brand: String,
//    val category: String,
//    val description: String,
//    val discountPercentage: Double,
//    val id: Int,
//    val images: List<String>,
//    val price: Int,
//    val rating: Double,
//    val stock: Int,
//    val thumbnail: String,
//    val title: String
) {
    override fun toString(): String {
        return title
    }
}