package News

data class NewsData(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
){

data class Article(
    val author: Any,
    val content: Any,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
}